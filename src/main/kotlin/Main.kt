import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import data.CountriesApiClient
import data.model.Country
import kotlinx.coroutines.launch
import org.jetbrains.skia.Image
import java.net.URL

fun loadImageFromUrl(url: String): ImageBitmap {
    return Image.makeFromEncoded(URL(url).readBytes()).toComposeImageBitmap()
}

@Composable
@Preview
fun App() {
    var countries by remember { mutableStateOf(listOf<Country>()) }
    var selectedCountry by remember { mutableStateOf<Country?>(null) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(true) {
        scope.launch {
            countries = CountriesApiClient.getEuropeanCountries()
        }
    }

    MaterialTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            if (selectedCountry == null) {
                Text("Країни Європи", modifier = Modifier.padding(bottom = 8.dp))
                for (country in countries) {
                    Text(
                        text = country.name.common,
                        modifier = Modifier
                            .clickable { selectedCountry = country }
                            .padding(vertical = 4.dp)
                    )
                }
            } else {
                Text("Назва: ${selectedCountry!!.name.common}", modifier = Modifier.padding(vertical = 4.dp))
                if (selectedCountry!!.capital.isNotEmpty()) {
                    Text("Столиця: ${selectedCountry!!.capital[0]}", modifier = Modifier.padding(vertical = 4.dp))
                }
                Text("Населення: ${selectedCountry!!.population}", modifier = Modifier.padding(vertical = 4.dp))
                Text("Площа: ${selectedCountry!!.area} км²", modifier = Modifier.padding(vertical = 4.dp))
                Image(
                    bitmap = loadImageFromUrl(selectedCountry!!.flags.png),
                    contentDescription = "Прапор",
                    modifier = Modifier
                        .height(100.dp)
                        .width(150.dp)
                        .padding(vertical = 8.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { selectedCountry = null }) {
                    Text("Назад")
                }
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Countries of Europe") {
        App()
    }
}
