import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.totd_final.R

class SignInScreen {
    private val redWineColor = Color.hsl(0F, .76F, .29F)
    private val lightGray = Color.hsl(0F, .0F, .95F)
    var userName = ""
    var isSignIn = false

    private fun textStyle(size: Int, color: Color = Color.White) =
        TextStyle(
            fontFamily = FontFamily(Font(R.font.readexpro)),
            fontSize = size.sp,
            color = color,
            textAlign = TextAlign.Center)

    @Composable
    fun showScreen (onClickSave: () -> Unit){
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.White)){
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally){
                logo()
                welcomeMessage()
                textField(onClickSave)
            }
        }
    }

    @Composable
    fun logo(){
        Box(modifier = Modifier.padding(top = 140.dp)){
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier
                        .size(86.dp)
                        .padding(),
                    painter = painterResource(id = R.drawable.myplan_logo),
                    contentDescription = "My plan logo" )
                Text(
                    text = "MyPlan", style = textStyle(34, redWineColor)
                )
            }
        }
    }

    @Composable
    fun welcomeMessage(){
        Box(modifier = Modifier.padding(top = 80.dp)){
            Text(text = "Welcome to MyPlan, ready to start?\nPlease write your name",style = textStyle(18, redWineColor)
            )
        }
    }

    @Composable
    fun textField(onClickSave: () -> Unit){
        var name by remember {
            mutableStateOf("")
        }

        Box(modifier = Modifier.padding(top = 10.dp)){
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TextField(
                    value = name,
                    onValueChange =  { newText -> name = newText },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = lightGray,
                        unfocusedContainerColor = lightGray,
                        disabledContainerColor = lightGray
                    ),
                    label = { Text(text = "Your name here") }
                )
                SaveButton(value = name, onClickSave)
            }
        }
    }

    @Composable
    fun SaveButton(value: String, onClickSave: () -> Unit){
        Box(modifier = Modifier.padding(top = 10.dp)){
            Button(
                onClick = {
                    userName = value
                    onClickSave()
                          },
                colors = ButtonDefaults.buttonColors(redWineColor),
            )
            {
                Text(text = "Continue", style = textStyle(12))
            }
        }
    }
}