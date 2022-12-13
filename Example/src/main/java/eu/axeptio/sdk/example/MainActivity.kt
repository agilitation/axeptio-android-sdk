package eu.axeptio.sdk.example

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import eu.axeptio.sdk.axeptio
import eu.axeptio.sdk.example.R


const val axeptioId = "624d5e22e4776e1f019014e2"
const val axeptioVersionFr = "axeptio-prod-fr"
const val axeptioVersionEn: String = "axeptio-prod-en"


const val demoId: String = "637f77ebb38394b040ab643e"
const val demoVersionEn: String = "test-en"
const val demoVersionfr: String = "test-fr"

const val basicId = "6058635aa6a92469bed037b0"
const val basicVersion = "ga_fb"

var clientId: String = axeptioId
var version: String = axeptioVersionFr

//const val clientId = "Your Client Id"
//const val version = "Your version"


class MainActivity : AppCompatActivity() {

    private lateinit var rootView: View
    private var dismissHandler: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        rootView = findViewById(R.id.rootView)
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<Button>(R.id.clearUserConsentsButton).setOnClickListener(::clearUserConsents)
        findViewById<Button>(R.id.showCookiesDialogButton).setOnClickListener(::showCookiesView)
        findViewById<Button>(R.id.newActivityButton).setOnClickListener(::newActivity)
        findViewById<Button>(R.id.swapIdentifiers).setOnClickListener(::swapIdentifers)

        if (savedInstanceState == null && !axeptio.isInitialized) {
            axeptio.initialize(clientId, version) { error ->
                if (error == null) {
                    showCookiesView()
                }
            }
        } else {
            showCookiesView()
        }
    }

    private fun clearUserConsents(sender: View? = null) {
        axeptio.clearUserConsents()
    }

    private fun showCookiesView(sender: View? = null) {
        this.dismissHandler?.invoke()
        this.dismissHandler =
            axeptio.showConsentView(onlyFirstTime = false, view = this.rootView) { error ->
                val result = axeptio.getUserConsent(vendorName = "google_analytics")
                println("Google Analytics consent is $result")
            }
    }

    private fun newActivity(sender: View? = null) {
        startActivity(Intent(this, MainActivity::class.java))
    }


    private fun swapIdentifers(sender: View? = null) {

        if (clientId.equals(axeptioId)) {
            clientId = demoId
            version = demoVersionEn
        } else {
            clientId = axeptioId
            version = axeptioVersionFr

        }
        axeptio.rerere(clientId, version) { error ->
            if (error == null) {
                showCookiesView()
            }
        }
    }
}

