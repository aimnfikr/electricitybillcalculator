package com.example.mobiletechprojnew

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobiletechprojnew.ui.theme.MobileTechProjNewTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobileTechProjNewTheme {
                // Initialize the NavController
                val navController = rememberNavController()

                // Set up the NavHost
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = "bill_calculator") {
                        composable("bill_calculator") { BillCalculator(navController) }
                        composable("about_page") { AboutPage() }
                    }
                }
            }
        }
    }
}

@Composable
fun BillCalculator(navController: NavController) {
    var units by remember { mutableStateOf(TextFieldValue("")) }
    var rebate by remember { mutableStateOf(TextFieldValue("")) }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Electricity Bill Calculator",
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ) {
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                BasicTextField(
                    value = units,
                    onValueChange = { units = it },
                    modifier = Modifier.fillMaxWidth(),
                    decorationBox = { innerTextField ->
                        Box(modifier = Modifier.padding(8.dp)) {
                            if (units.text.isEmpty()) {
                                Text(
                                    text = "Enter units used (kWh)",
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                                )
                            }
                            innerTextField()
                        }
                    }
                )

                BasicTextField(
                    value = rebate,
                    onValueChange = { rebate = it },
                    modifier = Modifier.fillMaxWidth(),
                    decorationBox = { innerTextField ->
                        Box(modifier = Modifier.padding(8.dp)) {
                            if (rebate.text.isEmpty()) {
                                Text(
                                    text = "Enter rebate percentage (0% - 5%)",
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }
        }

        Button(
            onClick = {
                val unitsValue = units.text.toDoubleOrNull()
                val rebateValue = rebate.text.toDoubleOrNull()

                result = if (unitsValue != null && rebateValue != null) {
                    val totalCharge = calculateTotalCharge(unitsValue)
                    val finalCost = totalCharge - (totalCharge * (rebateValue / 100))
                    "Total Charges: RM %.2f\nFinal Cost after Rebate: RM %.2f".format(
                        totalCharge,
                        finalCost
                    )
                } else {
                    "Please enter valid inputs."
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Calculate")
        }

        Button(
            onClick = {
                units = TextFieldValue("")
                rebate = TextFieldValue("")
                result = ""
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Reset")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = result,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 16.dp)
        )

        Button(
            onClick = {
                navController.navigate("about_page")
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("About")
        }
    }
}

@Composable
fun OpenUrl(url: String) {
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { _ ->
        }
    Text(
        text = "-GitHub-",
        style = MaterialTheme.typography.bodyLarge.copy(color = Color.Blue),
        textAlign = TextAlign.Center,
        modifier = Modifier.clickable {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            launcher.launch(intent)
        }
    )
}

@Composable
fun AboutPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Developer Details",
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.personalimage),
            contentDescription = "Your Image",
            modifier = Modifier.size(200.dp)
        )
        Text(
            text = "MUHAMMAD AIMAN FIKRI BIN AHMAD FAHMI",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Text(
            text = "2021461412",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Text(
            text = "RCS2515A",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Text(
            text = "This app is developed to help you calculate your electricity bill based on your usage and rebates. © 2024. All rights reserved.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Click -GitHub- below to access source code.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        OpenUrl("https://github.com/users/aimnfikr/projects/1")
    }
}

private fun calculateTotalCharge(units: Double): Double {
    var totalCharge = 0.0
    var remainingUnits = units

    if (remainingUnits > 200) {
        totalCharge += 200 * 0.218
        remainingUnits -= 200
    } else {
        totalCharge += remainingUnits * 0.218
        return totalCharge
    }

    if (remainingUnits > 100) {
        totalCharge += 100 * 0.334
        remainingUnits -= 100
    } else {
        totalCharge += remainingUnits * 0.334
        return totalCharge
    }

    if (remainingUnits > 300) {
        totalCharge += 300 * 0.516
        remainingUnits -= 300
    } else {
        totalCharge += remainingUnits * 0.516
        return totalCharge
    }

    totalCharge += remainingUnits * 0.546

    return totalCharge
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MobileTechProjNewTheme {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "bill_calculator") {
            composable("bill_calculator") { BillCalculator(navController) }
            composable("about_page") { AboutPage() }
        }
    }
}
