package user.register

import androidx.lifecycle.ViewModel

class RegisterViewModel(private val context: MainActivity): ViewModel() {
    fun register() {
        context.apply {
            binding.apply {
                if (!namesControl(firstName.text.toString()))
                    toastMessage(FIRST_NAME_ERROR)
                else if (!namesControl(lastName.text.toString()))
                    toastMessage(LAST_NAME_ERROR)
                else if (age.text.isEmpty() || age.text.toString().toInt() !in 18..100)
                    toastMessage(AGE_ERROR)
                else if (!Regex("[a-zA-Z]{2,}@[a-zA-Z]{2,}").containsMatchIn(email.text.toString()))
                    toastMessage(EMAIL_ERROR)
                else if (password.text.toString().trim().length < 5)
                    toastMessage(PASSWORD_ERROR)
                else if (password.text.toString() != passwordAgain.text.toString())
                    toastMessage(PASSWORD_AGAIN_ERROR)
                else
                    registerSuccess()
            }
        }
    }

    private fun namesControl(value: String): Boolean {
        return Regex("[a-zA-ZğĞİıöÖşŞüÜ]{2,}").containsMatchIn(value)
    }
}