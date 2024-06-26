package com.mvk.imageloader.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mvk.imageloader.ui.theme.ImageLoaderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  private val viewModel: MainViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      ImageLoaderTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          Greeting()
          viewModel.getAllImages()
        }
      }
    }
  }
}

@Composable
fun Greeting(
  modifier: Modifier = Modifier,
) {
  Text(
    text = "Hello",
    modifier = modifier
  )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  ImageLoaderTheme {
    Greeting()
  }
}