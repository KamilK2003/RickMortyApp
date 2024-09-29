package com.kamilk2003.rickmortyapp.viewmodel

import app.cash.turbine.test
import com.kamilk2003.rickmortyapp.helpers.BaseTest
import com.kamilk2003.rickmortyapp.modules.main.viewmodel.AppMainScreenViewModel
import com.kamilk2003.rickmortyapp.objects.models.Character
import com.kamilk2003.rickmortyapp.services.api.CharactersApiService
import com.kamilk2003.rickmortyapp.services.room.AppCRUDService
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.invoke
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AppMainScreenModelTest : BaseTest() {

    // MARK: - Stored Properties

    private lateinit var appMainScreenModel: AppMainScreenViewModel

    private val appCRUDService: AppCRUDService = mockk(relaxed = true)
    private val charactersApiService: CharactersApiService = mockk(relaxed = true)

    // MARK: - Test methods

    @BeforeEach
    override fun beforeEach() {
        super.beforeEach()
        appMainScreenModel = spyk(
            AppMainScreenViewModel(
                appCRUDService = appCRUDService,
                charactersApiService = charactersApiService
            )
        )
    }

    @AfterEach
    override fun afterEach() {
        super.afterEach()
        clearAllMocks()
    }

    @Test
    fun `changeRefreshingState updates state with the correct refreshing value`() = runTest {
        val isRefreshing = false
        appMainScreenModel.changeRefreshingState(isRefreshing)
        appMainScreenModel.state.test {
            assertTrue(this.expectMostRecentItem().isRefreshing.not())
        }
    }

    @Test
    fun `fetchCharacters updates state with characters and stops refreshing`() = runTest {
        val characters = makeCharactersList()

        every { appCRUDService.allCharacters.value } returns characters

        coEvery {
            charactersApiService.getCharacters(captureLambda())
        } answers {
            lambda<(List<Character>?) -> Unit>().invoke(characters)
        }

        appMainScreenModel.fetchCharacters()

        coVerify { charactersApiService.getCharacters(any()) }

        appMainScreenModel.state.test {
            val state = this.expectMostRecentItem()
            assertEquals(characters, state.characters)
            assertFalse(state.isRefreshing)
        }
    }

    @Test
    fun `fetchCharacters updates state with characters and stops refreshing, deprecated characters are removed from local database`() = runTest {
        val characters = makeCharactersList()
        val deprecatedCharacter = listOf(makeCharactersList().first().copy(name = "Jack"))

        every { appCRUDService.allCharacters.value } returns deprecatedCharacter
        coEvery {
            charactersApiService.getCharacters(captureLambda())
        } answers {
            lambda<(List<Character>?) -> Unit>().invoke(characters)
        }

        appMainScreenModel.fetchCharacters()

        coVerify { charactersApiService.getCharacters(any()) }

        val capturedDeprecatedCharacters = mutableListOf<List<Character>>()
        coVerify { appCRUDService.deleteCharacters(capture(capturedDeprecatedCharacters)) }

        assertTrue(capturedDeprecatedCharacters.first() == deprecatedCharacter)

        appMainScreenModel.state.test {
            val state = this.expectMostRecentItem()
            assertEquals(characters, state.characters)
            assertFalse(state.isRefreshing)
        }
    }

    @Test
    fun `switchFavouriteState deletes character from local database when it is already favourite`() = runTest {
        val characters = makeCharactersList()

        appMainScreenModel.updateState {
            it.copy(favouriteCharacters = characters)
        }

        appMainScreenModel.switchFavouriteState(characters.first())

        coVerify { appCRUDService.deleteCharacter(characters.first().id.toInt()) }
        coVerify(exactly = 0) { appCRUDService.insertCharacter(any()) }
    }

    @Test
    fun `switchFavouriteState inserts character to local database when it is not favourite`() = runTest {
        val characters = makeCharactersList()

        appMainScreenModel.updateState {
            it.copy(favouriteCharacters = listOf(characters.last()))
        }

        appMainScreenModel.switchFavouriteState(characters.first())

        coVerify { appCRUDService.insertCharacter(any()) }
        coVerify(exactly = 0) { appCRUDService.deleteCharacter(any()) }
    }

    private fun makeCharactersList(): List<Character> {
        return listOf(
            Character(
                id = 0,
                name = "Rick",
                image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
            ),
            Character(
                id = 1,
                name = "Mick",
                image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
            )
        )
    }
}