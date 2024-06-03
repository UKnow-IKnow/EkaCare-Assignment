package com.example.ekacareassignment

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.ekacareassignment.ui.theme.screen.AddInformationDialog
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate

@RunWith(AndroidJUnit4::class)
class AddInformationDialogTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testEmptyFieldsShowError() {
        composeTestRule.setContent {
            AddInformationDialog(
                onDismiss = {},
                onSave = {}
            )
        }

        // Click the Save button without filling in any fields
        composeTestRule.onNodeWithText("Save").performClick()

        // Wait for Compose to settle
        composeTestRule.waitForIdle()

        // Check if the error message is displayed
        composeTestRule.onNodeWithText("Please fill in all fields").assertIsDisplayed()
    }

    @Test
    fun testAllFieldsFilledNoError() {

        composeTestRule.setContent {
            AddInformationDialog(
                onDismiss = {},
                onSave = {}
            )
        }

        composeTestRule.onNodeWithText("Enter Name").performTextInput("John Doe")
        composeTestRule.onNodeWithText("Enter Age").performTextInput("30")

        val date = LocalDate.of(1990, 1, 1).toString()
        composeTestRule.onNodeWithText("Enter Date of Birth").performTextInput(date)
        composeTestRule.onNodeWithText("Address").performTextInput("123 Main St")


        composeTestRule.onNodeWithText("Save").performClick()

        composeTestRule.waitForIdle()


    }
}