package eu.axeptio.sdk.example

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import eu.axeptio.sdk.axeptio

class MainActivity : AppCompatActivity() {

    private lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        rootView = findViewById(R.id.rootView)
        findViewById<Button>(R.id.clearUserConsentsButton).setOnClickListener(::clearUserConsents)
        findViewById<Button>(R.id.showCookiesDialogButton).setOnClickListener(::showCookiesView)

        if (savedInstanceState == null && !axeptio.isInitialized) {
            axeptio.initialize(
                clientId = "<Replace with your client ID>",
                version = "<Replace with your version>"
            ) { error ->
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