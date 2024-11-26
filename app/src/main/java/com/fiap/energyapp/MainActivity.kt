package com.example.energyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fiap.energyapp.ui.theme.EnergyAppTheme

data class Appliance(val name: String, val consumption: Double)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EnergyAppTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    EnergyApp()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EnergyApp() {
    val appliances = remember { mutableStateListOf<Appliance>() }
    var name by remember { mutableStateOf("") }
    var consumption by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Cadastro de Eletrodomésticos", style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(16.dp))

        // Formulário
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nome do Eletrodoméstico") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = consumption,
            onValueChange = { consumption = it },
            label = { Text("Consumo Mensal (kW)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (name.isNotBlank() && consumption.isNotBlank()) {
                    appliances.add(Appliance(name, consumption.toDouble()))
                    name = ""
                    consumption = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Adicionar")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Lista de Eletrodomésticos
        Text("Eletrodomésticos Cadastrados", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(8.dp))

        if (appliances.isEmpty()) {
            Text("Nenhum eletrodoméstico cadastrado.")
        } else {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(appliances.size) { index ->
                    val appliance = appliances[index]
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        elevation = 4.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(appliance.name)
                            Text("${appliance.consumption} kW")
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Dicas de Economia
        Text("Dicas de Economia", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(getEnergyTips()) { tip ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    backgroundColor = MaterialTheme.colors.primary,
                    elevation = 4.dp
                ) {
                    Text(
                        tip,
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colors.onPrimary
                    )
                }
            }
        }
    }
}

fun getEnergyTips(): List<String> {
    return listOf(
        "Substitua lâmpadas incandescentes por LED.",
        "Desligue aparelhos que não estão sendo usados.",
        "Evite abrir a geladeira frequentemente.",
        "Use eletrodomésticos fora do horário de pico."
    )
}
