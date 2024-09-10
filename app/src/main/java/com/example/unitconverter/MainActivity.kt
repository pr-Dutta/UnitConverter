package com.example.unitconverter

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable // This composable allows us to use the @Composable keyword
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
//whatever comes after it should be rendered as user interface element
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext    //Imported later for LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unitconverter.ui.theme.UnitConverterTheme
import java.time.format.TextStyle
import kotlin.math.roundToInt

//Day - 7 (11-12-2023)
//Difference between XML and Jetpack Compose - Ask chatGPT - Done
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {//Here we override the implementation of onCreate()
        super.onCreate(savedInstanceState)      //We say that run the general implementation and the
        setContent {//setting content for UnitConverter                               //run out code
            UnitConverterTheme {//This will do the theme for our app, it is a composable
                //Composable is just a function with certain behaviour

                // A surface container uses the 'background' color from the theme
                //this will check where the right color theme, textures are used or not,
                Surface(    //surface is the background of our application
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Inside of the surface we have the text called Greetings Android
                    UnitConverter()
                }
            }
        }
    }
}


//Day - 8 (13-12-2023)
//This is our parent composable where we are going to put all the stuff
@Composable //Inside composable we can use other composable to
fun UnitConverter() {
    //Day - 13 (19-12-2023)
    var inputValue by remember { mutableStateOf("") }
    var outputValue by remember { mutableStateOf("") }
    var inputUnit by remember { mutableStateOf("Meter") }
    var outputUnit by remember { mutableStateOf("Meter") }
    var iExpanded by remember { mutableStateOf(false) }
    var oExpanded by remember { mutableStateOf(false) }
    val iConversionFactor = remember { mutableStateOf(1.00) }
    val oConversionFactor = remember { mutableStateOf(1.00) }

    //Day - 15  ,Need to add some dependencies
//    val customTextStyle = TextStyle(
//        fontFamily = FontFamily.Default, // Replace with your desired font family
//        fontSize = 16.sp, // Replace with your desired font size
//        color = Color.Red // Replace with your desired text color
//    )

    //Day - 14
    //You can create functions inside composable
    //Calculation of the conversion
    fun convertUnits() {
        //When we enter a not Double it wouldn't crash
        //?: - elvis operator
        val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0
        /* here whats going - the input "string" value will be converted to Double
        * if not double value entered then it will return null, and ?: this elvis
        * operator will do what, it will assign the value to inputValueDouble if the
        * value is double, but if the value goes to null it will assign 0.0 */

        /* Have to understand what's going on here */
        val result = (inputValueDouble * iConversionFactor.value * 100.0 / oConversionFactor.value).roundToInt() / 100.0
        outputValue = result.toString()

        /* I am going to develop my own calculator app */
    }


    Column(
        //Day - 9 (14-12-2023)
        /*Modifier :- throw modifier we can change the padding, color, size, positioning etc
            *of an UI element*/
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Here all the UI elements will be stacked below each other

        //Day - 15
        Text("Unit Converter", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))   //modifier = Modifier.padding(40.dp)

        //I just changed the value of Outline text field

        OutlinedTextField(value = inputValue, onValueChange = {
                inputValue = it     //Day - 14
                convertUnits()
                /* what will on value change give us is this it value which is of type String
                * which is basically the thing ones the value changed, what ever we enter
                * into our OutlineField that is "it" */


                //What is this code execution peach:- It's an anonymous function
                //Anonymous function is a function without a name, We can't call it but we can execute it.
                // Here what should happen, when the Valur of our OutlinedTextField changes
            },
            label = { Text(text = "Enter Value" )})

        /* when we enter something on to outline text field it will automatically
        * reflect on to the UI */

        /*In order to display a toast we need context where to display it,
        * Context - In which area should something happen,
        * I would say it should be used within a composable function,
        * so the context should be inside of the local context of my current composable*/
        Spacer(modifier = Modifier.height(16.dp))
        Row {

            /*A box is a layout element just like row and column
            * Dropdown needs a parent to position itself in to the screen*/

            //Input Box
            Box {

                //Day - 14
                //Input Button

                /*The Box composable is a container that can hold other composable
                and allows you to position and stack them within a bounding box.*/
                Button(onClick = { iExpanded = true }) {
                    Text(text = inputUnit)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Arrow Down")
                    //Content description will be read out if you use the accessibility feature
                }

                //Day - 10 (15-12-2023)
                /*onDismissRequest:- called when the user requests to dismiss the menu,
                *such as by tapping outside the menu's bounds*/

                //Day - 14
                DropdownMenu(expanded = iExpanded, onDismissRequest = { iExpanded = false }) {
                    DropdownMenuItem(
                        text = { Text("Centimeter") },
                        /*These text expecting a Composable as parameter*/
                        onClick = {
                            iExpanded = false
                            inputUnit = "Centimeter"
                            iConversionFactor.value = 0.01   // 0.01 centimeter is 1 meter
                            convertUnits()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Meters") },
                        onClick = {
                            iExpanded = false
                            inputUnit = "Meter"
                            iConversionFactor.value = 1.0    // 1 Meter is our base Unit
                            convertUnits()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Feet") },
                        onClick = {
                            iExpanded = false
                            inputUnit = "Feet"
                            iConversionFactor.value = 0.3048   //0.3048 feet is 1 meter - 30 cm 480 mm
                            convertUnits()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Milimeters") },
                        onClick = {
                            iExpanded = false
                            inputUnit = "Milimeters"
                            iConversionFactor.value = 0.001   //0.001 mm is 1 meter
                            convertUnits()
                        }
                    )
                }
            }


            // 8, 16 and 32 are recommended spacers during UI design in Android

            Spacer(modifier = Modifier.width(16.dp))

            //Output Box
            Box {
                //Output Button
                Button(onClick = { oExpanded = true }) {
                    Text(text = outputUnit)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Arrow Down")
                }
                DropdownMenu(expanded = oExpanded, onDismissRequest = { oExpanded = false }) {
                    DropdownMenuItem(
                        text = { Text("Centimeter") },
                        /*These text expecting a Composable as parameter*/
                        onClick = {
                            oExpanded = false
                            outputUnit = "Centimeter"
                            oConversionFactor.value = 0.01
                            convertUnits()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Meters") },
                        onClick = {
                            oExpanded = false
                            outputUnit = "Meters"
                            oConversionFactor.value = 1.00
                            convertUnits()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Feet") },
                        onClick = { oExpanded = false
                            outputUnit = "Feet"
                            oConversionFactor.value = 0.3048
                            convertUnits() }
                    )
                    DropdownMenuItem(
                        text = { Text("Milimeters") },
                        onClick = {
                            oExpanded = false
                            outputUnit = "Milimeters"
                            oConversionFactor.value = 0.001
                            convertUnits()
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        //Day - 15
        Text("Result: $outputValue $outputUnit",
                style = MaterialTheme.typography.headlineSmall
            )
    }
}

/* Important Note For State Management:- Keywords to remember for state management:-
* Remember and Mutable State we are going to make variables for every state, we want
* to remember and we can update our UI based on those changing*/

@Preview(showBackground = true)
@Composable
fun UnitConverterPreview() {
    UnitConverter()
}