package com.hannoobz.internship.ui.firstscreen

import android.app.AlertDialog
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FirstScreenViewModel : ViewModel() {
    private val _nameInput = MutableLiveData<String>().apply {
        value = ""
    }
    val nameInput: MutableLiveData<String> = _nameInput

    private val _palindromeInput= MutableLiveData<String>().apply {
        value = ""
    }
    val palindromeInput: MutableLiveData<String> = _palindromeInput

    private fun checkPalindrome() : Boolean{
//      My leetcode sub
//      https://leetcode.com/u/dungeon_wyvern/
//      https://leetcode.com/submissions/detail/1636249003/

        val re = Regex("[^A-Za-z0-9 ]")
        val stringToBeChecked = re.replace(palindromeInput.value?.replace(" ","")?.lowercase() ?: "","")
        var leftPointer = 0
        var rightPointer = stringToBeChecked.length-1
        while(leftPointer<=rightPointer){
            if (stringToBeChecked[leftPointer]==stringToBeChecked[rightPointer]){
                leftPointer++
                rightPointer--
            }
            else{
                return false
            }
        }
        return true
    }

    fun checkPalindromeShow(context: Context){
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        with(builder) {
            if (checkPalindrome()) {
                setMessage("isPalindrome")
            } else {
                setMessage("not palindrome")
            }
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}