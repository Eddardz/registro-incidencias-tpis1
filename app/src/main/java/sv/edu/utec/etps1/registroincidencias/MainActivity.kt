
package sv.edu.utec.etps1.registroincidencias

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sv.edu.utec.etps1.registroincidencias.ui.theme.RegistroIncidenciasTheme
import java.util.Locale
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RegistroIncidenciasTheme {
                RegistroIncidenciasApp()
            }
        }
    }
}

@Composable
fun RegistroIncidenciasApp() {

    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }

    var mensaje by remember {
        mutableStateOf("Aún no hay reporte creado")
    }

    var prioridad by remember {
        mutableStateOf("No seleccionada")
    }

    var valoresSensor by remember {
        mutableStateOf("Esperando lectura del acelerómetro...")
    }

    var interpretacionSensor by remember {
        mutableStateOf("Esperando datos para realizar el diagnóstico.")
    }

    var sensorDisponible by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    DisposableEffect(context) {
        val sensorManager = context.getSystemService(
            Context.SENSOR_SERVICE
        ) as SensorManager

        val acelerometro = sensorManager.getDefaultSensor(
            Sensor.TYPE_ACCELEROMETER
        )

        var listener: SensorEventListener? = null

        if (acelerometro == null) {
            sensorDisponible = false
            valoresSensor =
                "Acelerómetro no disponible en este dispositivo."
            interpretacionSensor =
                "No es posible realizar el diagnóstico sin el sensor."
        } else {
            val sensorListener = object : SensorEventListener {

                override fun onSensorChanged(event: SensorEvent) {
                    val x = event.values[0]
                    val y = event.values[1]
                    val z = event.values[2]

                    valoresSensor = String.format(
                        Locale.getDefault(),
                        "X: %.2f m/s²\nY: %.2f m/s²\nZ: %.2f m/s²",
                        x,
                        y,
                        z
                    )

                    // Calcula la magnitud total de la aceleración.
                    val magnitud = sqrt(
                        x * x + y * y + z * z
                    )

                    // Aproximadamente 9.81 m/s² corresponde
                    // a la gravedad cuando el teléfono está quieto.
                    interpretacionSensor = if (
                        magnitud < 8.5f || magnitud > 11.2f
                    ) {
                        "Movimiento o cambio de aceleración detectado."
                    } else {
                        "Sin cambios significativos de aceleración."
                    }
                }

                override fun onAccuracyChanged(
                    sensor: Sensor?,
                    accuracy: Int
                ) {
                    // No se necesita realizar una acción adicional.
                }
            }

            listener = sensorListener

            val registrado = sensorManager.registerListener(
                sensorListener,
                acelerometro,
                SensorManager.SENSOR_DELAY_NORMAL
            )

            if (registrado) {
                sensorDisponible = true
            } else {
                sensorDisponible = false
                valoresSensor =
                    "No se pudo iniciar la lectura del acelerómetro."
                interpretacionSensor =
                    "El diagnóstico no está disponible."
            }
        }

        onDispose {
            listener?.let {
                sensorManager.unregisterListener(it)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Reporte de recolección de basura",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Registra problemas relacionados con la recolección " +
                    "de basura y permite dar seguimiento al reporte."
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Nuevo reporte",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = titulo,
                    onValueChange = { titulo = it },
                    label = { Text("Título") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Next
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Done
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Selecciona una prioridad:",
                    style = MaterialTheme.typography.titleSmall
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { prioridad = "Baja" }
                    ) {
                        Text(
                            text = "Baja",
                            modifier = Modifier.padding(12.dp)
                        )
                    }

                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { prioridad = "Media" }
                    ) {
                        Text(
                            text = "Media",
                            modifier = Modifier.padding(12.dp)
                        )
                    }

                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { prioridad = "Alta" }
                    ) {
                        Text(
                            text = "Alta",
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Prioridad seleccionada: $prioridad")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Diagnóstico del dispositivo",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = if (sensorDisponible) {
                        "Estado: Acelerómetro disponible"
                    } else {
                        "Estado: Sensor no disponible o sin lectura"
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Lecturas del acelerómetro",
                    style = MaterialTheme.typography.titleSmall
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = valoresSensor)

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Interpretación: $interpretacionSensor",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                mensaje = if (titulo.isBlank()) {
                    "Ingresa un título para el reporte"
                } else {
                    "Reporte preparado: $titulo"
                }
            }
        ) {
            Text("Crear reporte")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = mensaje)

        Spacer(modifier = Modifier.height(12.dp))

        Text(text = "Prototipo inicial — Unidad 1")
    }
}

@Preview(showBackground = true)
@Composable
fun RegistroIncidenciasPreview() {
    RegistroIncidenciasTheme {
        RegistroIncidenciasApp()
    }
}
