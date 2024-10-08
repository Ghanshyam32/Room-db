package com.ghanshyam.room.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ghanshyam.room.R
import com.ghanshyam.room.model.User
import com.ghanshyam.room.viewmodel.UserViewModel

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var btn: Button
    private lateinit var fn: EditText
    private lateinit var ln: EditText
    private lateinit var age: EditText
    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_update, container, false)
        btn = view.findViewById(R.id.update_btn)
        fn = view.findViewById<EditText>(R.id.updateFirstName)
        ln = view.findViewById<EditText>(R.id.updateLastName)
        age = view.findViewById<EditText>(R.id.updateAge)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        fn.setText(args.customUser.firstName)
        ln.setText(args.customUser.lastName)
        age.setText(args.customUser.age.toString())

        btn.setOnClickListener {
            update()
        }
        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        return view
    }

    private fun update() {
        val firstName = fn.text.toString()
        val lastName = ln.text.toString()
        val age = Integer.parseInt(age.text.toString())

        if (inputCheck(firstName, lastName, age.toString())) {
            val updatedUser = User(args.customUser.id, firstName, lastName, age)
            mUserViewModel.updateUser(updatedUser)
            Toast.makeText(requireContext(), "Updated Successfully", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_blankFragment)
        } else {
            Toast.makeText(requireContext(), "Fill out the details", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(firstName: String, lastName: String, age: String): Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && TextUtils.isEmpty(
            age
        ))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mUserViewModel.deleteUser(args.customUser)
            Toast.makeText(
                requireContext(),
                "Successfully removed: ${args.customUser.firstName}",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_updateFragment_to_blankFragment)
        }
        builder.setNegativeButton("No") { _, _ ->

        }
        builder.setTitle("Delete ${args.customUser.firstName}?")
        builder.setMessage("Are sure you want to delete ${args.customUser.firstName}")
        builder.create().show()
    }

}
