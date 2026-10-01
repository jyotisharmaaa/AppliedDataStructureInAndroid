package com.jyoti.appliedDataStructure.cyclic_rotation_in_array.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jyoti.appliedDataStructure.cyclic_rotation_in_array.data.Step


@Composable
fun CyclicSortScreen() {
    val examples = listOf(
        listOf(1, 3, 4, 2, 2),
        listOf(3, 4, 1, 2, 4),
        listOf(2, 5, 1, 3, 5, 4)
    )
    var exampleIdx by remember { mutableIntStateOf(0) }
    val steps = remember(exampleIdx) { cyclicSortSteps(examples[exampleIdx]) }
    var stepIdx by remember(exampleIdx) { mutableIntStateOf(0) }
    val step = steps[stepIdx]

    Column(
        Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Find Duplicate with Cyclic Sort", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text("Step ${stepIdx + 1} of ${steps.size}")

        // The array drawn as boxes (index shown below each box)
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            step.arr.forEachIndexed { idx, value ->
                val color = when {
                    step.duplicate != null && (idx == step.i || idx == step.j) ->
                        MaterialTheme.colorScheme.errorContainer
                    idx == step.i -> MaterialTheme.colorScheme.primaryContainer
                    idx == step.j -> MaterialTheme.colorScheme.tertiaryContainer
                    else -> MaterialTheme.colorScheme.surfaceVariant
                }
                Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(color),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("$value", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    }
                    Text("idx $idx", style = MaterialTheme.typography.labelSmall)
                }
            }
        }

        // Explanation of the current step
        Card(Modifier.fillMaxWidth()) {
            Text(step.message, Modifier.padding(16.dp), fontSize = 16.sp)
        }

        // Controls
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(onClick = { stepIdx-- }, enabled = stepIdx > 0) { Text("Back") }
            Button(onClick = { stepIdx++ }, enabled = stepIdx < steps.lastIndex) { Text("Next") }
            TextButton(onClick = { exampleIdx = (exampleIdx + 1) % examples.size }) {
                Text("Try another array")
            }
        }

        Spacer(Modifier.weight(1f))

        // The "why recruiters care" card
        Card(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("HashSet:  O(n) time, O(n) extra memory")
                Text("Cyclic sort:  O(n) time, O(1) extra memory", fontWeight = FontWeight.Bold)
            }
        }
    }
}
fun cyclicSortSteps(input: List<Int>): List<Step> {
    val a = input.toMutableList()
    val steps = mutableListOf(
        Step(a.toList(), 0, null, "Start. Rule: number v must sit at index v-1.")
    )
    var i = 0
    while (i < a.size) {
        val v = a[i]
        if (v == i + 1) {
            steps += Step(a.toList(), i, null, "$v is already at index $i. Move to next index.")
            i++
        } else {
            val j = v - 1
            if (a[j] == v) {
                steps += Step(
                    a.toList(), i, j,
                    "Index $j already holds $v, and index $i also has $v. DUPLICATE = $v",
                    duplicate = v
                )
                return steps
            }
            val other = a[j]
            a[i] = other
            a[j] = v
            steps += Step(a.toList(), i, j, "$v belongs at index $j. Swapped index $i and $j.")
        }
    }
    return steps
}
