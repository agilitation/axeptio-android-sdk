package eu.axeptio.sdk.example

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import eu.axeptio.sdk.axeptio

const val clientId = "6058635aa6a92469bed037b0"
const val version = "ga_fb"
//const val clientId = "623d7eadac49c0081cb74d28"
//const val version = "veracash ios-fr"
//const val clientId = "62b0930502449e7f75501de8"
//const val version = "whitelabel club app -en"

//const val clientId = "Your Client Id"
//const val version = "Your version"

class MainActivity : AppCompatActivity() {

    private lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        rootView = findViewById(R.id.rootView)
        findViewById<Button>(R.id.clearUserConsentsButton).setOnClickListener(::clearUserConsents)
        findViewById<Button>(R.id.showCookiesDialogButton).setOnClickListener(::showCookiesView)

        if (savedInstanceState == null && !axeptio.isInitialized) {
            axeptio.initialize( clientId, version) { error ->
                if (error == null) {
                    showAxeptioConsentView()
                }
            }
        } else {
            showAxeptioConsentView()
        }
    }

    private fun showAxeptioConsentView() {
        axeptio.showConsentView(view = this.rootView) {
            val result = axeptio.getUserConsent(vendorName = "google_analytics")
            println("Google Analytics consent is $result")
        }
    }

    private fun clearUserConsents(sender: View? = null) {
        axeptio.clearUserConsents()
    }

    private fun showCookiesView(sender: View? = null) {
        axeptio.showConsentView(onlyFirstTime = sender == null, view = this.rootView) { error ->
            val result = axeptio.getUserConsent(vendorName = "google_analytics")
            println("Google Analytics consent is $result")
        }
    }
}