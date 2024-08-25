package com.ghanshyam.room.fragments.add

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ghanshyam.room.R
import com.ghanshyam.room.model.User
import com.ghanshyam.room.viewmodel.UserViewModel

class AddFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val btn = view.findViewById<Button>(R.id.add_btn)
        val fn = view.findViewById<EditText>(R.id.updateFirstName)
        val ln = view.findViewById<EditText>(R.id.updateLastName)
        val age = view.findViewById<EditText>(R.id.updateAge)

        btn.setOnClickListener {
            insertDataToDb(fn, ln, age)
        }

        return view
    }

    private fun insertDataToDb(fn: EditText, ln: EditText, age: EditText) {
        val firstName = fn.text.toString()
        val lastName = ln.text.toString()
        val ageText = age.text

        if (inputCheck(firstName, lastName, ageText)) {
            val user = User(0, firstName, lastName, Integer.parseInt(ageText.toString()))

            mUserViewModel.addUser(user)
            Toast.makeText(requireContext(), "Successfully Added!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_blankFragment2_to_blankFragment)
        } else {
            Toast.makeText(requireContext(), "Fill out all fields", Toast.LENGTH_SHORT).show()
        }

    }

    private fun inputCheck(firstName: String, lastName: String, age: Editable): Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && age.isEmpty())
    }

}
