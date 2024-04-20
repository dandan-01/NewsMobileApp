package com.example.news_finalproject.api.jsoup

import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

@Composable
fun WebViewWithCss() {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK // loads the resource from the network
                webViewClient = WebViewClient() // responsible for handling page navigation events, such as when the user clicks on a link
            }
        }
    ) { webView ->
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // URL of the webpage to parse
                val url = "https://www.bankrate.com/investing/how-to-invest-in-cryptocurrency-beginners-guide/"

                // Fetch HTML content from the webpage using GET
                val document = Jsoup.connect(url).get()

                // Remove top header from HTML content
                // in this case it's enclosed in a <div> tag
                document.select("div.SiteNav-wrapper").remove()


                // Find all <link rel="stylesheet"> elements to fetch CSS
                val cssLinks = document.select("link[rel=stylesheet]")

                // StringBuilder to hold all CSS
                val allCss = StringBuilder()

                // Fetch each CSS linked in the HTML and append it to allCss
                cssLinks.forEach { link ->
                    val cssUrl = link.absUrl("href") // Ensure the URL is absolute
                    try {
                        val cssContent =
                            Jsoup.connect(cssUrl).ignoreContentType(true).execute().body()
                        allCss.append(cssContent).append("\n")
                    } catch (e: Exception) {
                        // Handle CSS fetch error
                    }
                }

                // Inject the fetched CSS into a <style> tag in the document's <head>
                document.head().appendElement("style").text(allCss.toString())

                // Convert the modified document to a string
                val htmlContentWithInlineCss = document.toString()

                // Load the modified HTML into the WebView on the main thread
                withContext(Dispatchers.Main) {
                    webView.loadDataWithBaseURL(
                        "https://www.bankrate.com/investing/how-to-invest-in-cryptocurrency-beginners-guide/",
                        htmlContentWithInlineCss,
                        "text/html",
                        "UTF-8",
                        null
                    )
                }
            } catch (e: Exception) {
                // Handle exceptions
            }
        }
    }
}