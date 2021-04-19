package eu.axeptio.sdk.example

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import eu.axeptio.sdk.axeptio

class MainActivity : AppCompatActivity() {

    private lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        rootView = findViewById(R.id.rootView)

        axeptio.initialize(
            clientId = "<Replace with your client ID>",
            version = "<Replace with your version>"
        ) { error ->
            if (error != null) {
                return@initialize
            }
            axeptio.showConsentView(view = this.rootView) {
                val result = axeptio.getUserConsent(vendorName = "google_analytics")
                print("Google Analytics consent is $result")
            }
        }
    }
}