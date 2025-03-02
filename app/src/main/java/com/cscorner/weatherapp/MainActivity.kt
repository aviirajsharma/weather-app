 package com.cscorner.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cscorner.weatherapp.ui.theme.BlueJC
import com.cscorner.weatherapp.ui.theme.DarkBlueJC
import com.cscorner.weatherapp.ui.theme.WeatherAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        var keepSplash = true
        splashScreen.setKeepOnScreenCondition { keepSplash }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppTheme {
                LaunchedEffect(Unit) {
                    kotlinx.coroutines.delay(2000)  // Delay for 2 seconds
                    keepSplash = false
                }
                WeatherScreen()
            }
        }
    }
}


@Composable
 fun WeatherScreen(){
     val viewModel :WeatherViewModel = viewModel()
    val weatherData by viewModel.weatherData.collectAsState()
    var city by remember {
        mutableStateOf("")
    }
    val apiKey = "481e9e8c8bb6ef7df804e66b18490b58"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(painterResource(id = R.drawable.weather_main_screen2),
                contentScale = ContentScale.FillBounds)
    ){
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(250.dp))
            OutlinedTextField(value = city,
                onValueChange = {city = it},
                label = { Text("City") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(30.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = BlueJC,
                    unfocusedIndicatorColor = BlueJC,
                    focusedLabelColor = DarkBlueJC,
                    cursorColor = DarkBlueJC,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    viewModel.fetchWeather(city,apiKey)
                },
                colors = ButtonDefaults.buttonColors(BlueJC)
            ) {
                Text("Check Weather")
            }
            Spacer(modifier = Modifier.height(16.dp))
            weatherData?.let {
                Row(
                    modifier =  Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    WeatherCard(label = "City", value = it.name, icon = Icons.Default.Place)
                    WeatherCard(label = "Temperature", value = "${it.main.temp}°C", icon = Icons.Default.Star)
                }
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ){
                    WeatherCard(label = "Humidity", value = "${it.main.humadity}%", icon = Icons.Default.Warning)
                    WeatherCard(label = "Pressure", value = "${it.main.pressure}hPa", icon = Icons.Default.KeyboardArrowRight)
                }
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ){
                    WeatherCard(label = "Feels Like", value = "${it.main.feels_like}°C", icon = Icons.Default.Face)
                    WeatherCard(label = "Description", value = it.weather[0].description, icon = Icons.Default.Info)
                }
            }
        }
    }

 }

 @Composable
 fun WeatherCard(label:String, value:String, icon: ImageVector){
     Card (
         modifier = Modifier
             .padding(8.dp)
             .height(115.dp)
             .width(150.dp),
         colors = CardDefaults.cardColors(Color.White),
         elevation = CardDefaults.cardElevation(4.dp)
     ){
         Column (
             modifier = Modifier
                 .padding(16.dp)
                 .fillMaxSize(),
             horizontalAlignment = Alignment.Start,
             verticalArrangement = Arrangement.Top
         ){
             Row (
                 verticalAlignment = Alignment.CenterVertically,
                 horizontalArrangement = Arrangement.Start
             ){
                 Icon(imageVector = icon,
                     contentDescription = null,
                     tint = DarkBlueJC,
                     modifier = Modifier
                         .size(20.dp)
                 )
                 Spacer(modifier = Modifier.width(4.dp))
                 Text(text = label, color = DarkBlueJC, fontSize = 14.sp)
             }
             Spacer(modifier = Modifier.height(8.dp))
             Box(
                 modifier = Modifier
                     .fillMaxWidth()
                     .weight(1f),
                 contentAlignment = Alignment.Center
             ){
                 Text(text = value,
                     fontSize = 22.sp,
                     fontWeight = FontWeight.Bold,
                     textAlign = TextAlign.Center,
                     color = BlueJC
                 )
             }

         }
     }
 }
