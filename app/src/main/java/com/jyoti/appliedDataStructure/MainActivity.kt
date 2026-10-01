package com.jyoti.appliedDataStructure

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import com.jyoti.appliedDataStructure.cyclic_rotation_in_array.ui.CyclicSortScreen

// ───────────── 1. The algorithm, recorded step by step ─────────────
//
// Problem: array of size n+1 holds numbers from 1..n, so at least one repeats.
// Idea (Cyclic Sort): number v belongs at index v-1.
//   - If it is already there -> move on.
//   - Else look at index v-1:
//        * if that spot already holds v -> v is the DUPLICATE
//        * otherwise swap, so v lands in its home.

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MaterialTheme { CyclicSortScreen() } }
    }
}

