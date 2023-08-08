package com.example.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import android.widget.GridLayout
import android.widget.Toast


class MainActivity : ComponentActivity() {

    private lateinit var gridLayout: GridLayout
    private val boardState = Array(3) { Array(3) { -1 } }


    // Assuming you have a TextView with id player1Label

    private var player1: String? = null
    private var player2: String? = null
    private var turn = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.start_screen)

        val player1NameEditText = findViewById<EditText>(R.id.player1Name)
        val player2NameEditText = findViewById<EditText>(R.id.player2Name)
        val startButton = findViewById<Button>(R.id.startButton)

        val player1NameToShow = findViewById<TextView>(R.id.player1Name)

        startButton.setOnClickListener(View.OnClickListener {
            println("buenas entre aqui")
            // Get player names from EditText fields
            player1 = player1NameEditText.getText().toString()
            player2 = player2NameEditText.getText().toString()

            //after start the game
            setContentView(R.layout.tic_tac_toe)

            val player1Label: TextView = findViewById(R.id.player1Label)
            val player2Label: TextView = findViewById(R.id.player2Label)
            val turnView: TextView = findViewById(R.id.whosTurn)


            player1Label.setText("Player 1: ${player1}")
            player2Label.setText("Player 2: ${player2}")

            // to play the game
            gridLayout = findViewById(R.id.gridLayout)

            // if 0 player 1 plays first and viceversa
            turn = getRandomZeroOrOne()

            println(getRandomZeroOrOne())
            println(getRandomZeroOrOne())
            println(getRandomZeroOrOne())
            println(getRandomZeroOrOne())


            turnView.setText("turn: Player ${turn + 1}")


            start(turnView)






            // Transition to game board activity or update UI
            // (This is where you start the actual game)
        })
    }

    /*private fun setBoardSize(size: Int) {
        boardState = Array(size) { Array(size) { -1 } } // Initialize the board state
        gridLayout.rowCount = size
        gridLayout.columnCount = size
    }*/

    fun getRandomZeroOrOne(): Int {
        return (0..1).random()
    }

    private fun start(textView: TextView) {
        for (row in 0 until gridLayout.rowCount) {
            for (col in 0 until gridLayout.columnCount) {
                val cellButton = gridLayout.getChildAt(row * 3 + col) as Button
                cellButton.setOnClickListener {
                    if (boardState[row][col] == -1) {
                        boardState[row][col] = turn
                        cellButton.text = if (turn == 0) "X" else "O"
                        turn = (turn + 1) % 2
                        checkForWin()
                        textView.setText("turn: Player ${turn + 1}")
                    }
                }
            }
        }
    }

    private fun checkForWin() {
        val winningCombinations = arrayOf(
            intArrayOf(0, 1, 2), intArrayOf(3, 4, 5), intArrayOf(6, 7, 8), // Rows
            intArrayOf(0, 3, 6), intArrayOf(1, 4, 7), intArrayOf(2, 5, 8), // Columns
            intArrayOf(0, 4, 8), intArrayOf(2, 4, 6) // Diagonals
        )

        for (combination in winningCombinations) {
            val cellValues = combination.map { (it / 3) to (it % 3) }.map { (row, col) -> boardState[row][col] }
            if (cellValues.all { it == 0 } || cellValues.all { it == 1 }) {
                val winner = if (turn == 0) "Player 2" else "Player 1"
                Toast.makeText(this, "$winner wins!", Toast.LENGTH_SHORT).show()
                // Here you might want to reset the game or perform other actions
            }
        }
    }




}