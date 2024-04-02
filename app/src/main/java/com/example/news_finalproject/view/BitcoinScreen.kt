package com.example.news_finalproject.view

import androidx.compose.runtime.Composable

@Composable
fun BitcoinScreen(bitcoinManager: BitcoinManager, navController: NavHostController) {
    val bitcoinNews = bitcoinManager.bitcoinNewsResponse.value

    Column(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp)
            .verticalScroll(rememberScrollState()) // Enable scrolling
    ) {
        // Bitcoin News header
        Text(
            text = "Bitcoin News",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
        )

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(bitcoinNews) { article ->
                BitcoinNewsCard(newsItem = article, navController)
            }
        }
    }
}
