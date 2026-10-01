package com.aulasandroid.calculadora_imc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aulasandroid.calculadora_imc.ui.theme.Calculadora_IMCTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Calculadora_IMCTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    IMCScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun IMCScreen(modifier: Modifier = Modifier) {
    var altura by remember { mutableStateOf("1.79") }
    var peso by remember { mutableStateOf("25") }
    var status by remember { mutableStateOf("") }
    var imc by remember { mutableStateOf(20.0) }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // --- header -----
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(color = colorResource(id = R.color.cor_app)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.bmi),
                contentDescription = "Logo do app",
                modifier = Modifier
                    .size(80.dp)
                    .padding(vertical = 16.dp)
            )

            Text(
                text = "Calculadora IMC",
                fontSize = 24.sp,
                color = Color.White
            )
        }

        // --- Formulário -----
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-30).dp)
                    .height(350.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF9F6F6)
                ),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "Seus dados",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp),
                        color = Color(0xFF4BA5D5)
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        value = altura,
                        onValueChange = { novoValor ->
                            altura = novoValor
                        },
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF499FCC),
                            unfocusedBorderColor = Color(0xFF499FCC),
                            focusedLabelColor = Color(0xFF499FCC),
                            unfocusedLabelColor = Color.Gray
                        ),
                        placeholder = { Text(text = "Altura") }
                    )

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = peso,
                        onValueChange = { novoValor ->
                            peso = novoValor
                        },
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF499FCC),
                            unfocusedBorderColor = Color(0xFF499FCC),
                            focusedLabelColor = Color(0xFF499FCC),
                            unfocusedLabelColor = Color.Gray
                        ),
                        placeholder = { Text(text = "Peso") }
                    )

                    // Botão Calcular
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp),
                        onClick = {
                            val imc = calcularIMC(peso, altura)

                            // 2. Classifica o status com base no resultado obtido
                            status = if (imc < 18.5) {
                                "Abaixo do peso"
                            } else if (imc in 18.5..24.9) {
                                "Peso normal"
                            } else if (imc in 25.0..29.9) {
                                "Sobrepeso"
                            } else {
                                "Obesidade"
                            }

                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF499FCC),
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "Calcular", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    }

                    //Botão limpar dados
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF0E597C),
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "Limpar dados", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF23883C)
                ),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Text(
                    text = "$peso $status",
                    color = Color.White,
                    modifier = Modifier.padding(8.dp)
                )

            }
        }
    }
}

fun calcularIMC(peso: String, altura: String): Double {
    val peso = peso.toDoubleOrNull() ?: 0.0
    val altura = altura.toDoubleOrNull() ?: 0.0

    if (altura == 0.0) return 0.0

    return peso / (altura * altura)
}

