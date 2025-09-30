package com.example.appcalculadorakotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appcalculadorakotlin.ui.theme.AppCalculadoraKotlinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Appcalculadora()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Appcalculadora() {
    // Estados para almacenar los valores numéricos
    var num1 by remember { mutableStateOf("") }
    var num2 by remember { mutableStateOf("") }

    // Estado para almacenar la operación seleccionada
    var selectedOperation by remember { mutableStateOf("suma") }

    // Estado para almacenar el resultado
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Campo de edición para el primer número
        OutlinedTextField(
            value = num1,
            onValueChange = { num1 = it },
            label = { Text("Número 1") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de edición para el segundo número
        OutlinedTextField(
            value = num2,
            onValueChange = { num2 = it },
            label = { Text("Número 2") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // RadioButtons para seleccionar la operación
        val operaciones = listOf("suma", "resta", "multiplicación", "división")

        operaciones.forEach { operacion ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(4.dp)
            ) {
                RadioButton(
                    selected = (selectedOperation == operacion),
                    onClick = { selectedOperation = operacion }
                )

                Text(
                    text = operacion,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para calcular
        Button(onClick = { result = calcula(num1, num2, selectedOperation) }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Calcular")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar resultado
        Text(text = result, style = MaterialTheme.typography.bodyLarge)
    }
}

// Función para realizar cálculos
fun calcula(num1: String, num2: String, operacion: String): String {
    return try {
        val n1 = num1.toDouble()
        val n2 = num2.toDouble()
        when (operacion) {
            "suma" -> "Resultado: ${n1 + n2}"
            "resta" -> "Resultado: ${n1 - n2}"
            "multiplicación" -> "Resultado: ${n1 * n2}"
            "división" ->
                if (n2 != 0.0)
                    "Resultado: ${n1 / n2}"
                else
                    "Error: división por cero"
            else -> "Error: operación no válida"
        }
    } catch (e: NumberFormatException) {
        "Error: ingresa números válidos"
    }
}
