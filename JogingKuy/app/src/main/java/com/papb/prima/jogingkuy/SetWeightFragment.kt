package com.papb.prima.jogingkuy


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.NumberPicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_set_height.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class SetWeightFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_set_weight, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val next = view.findViewById<ImageView>(R.id.nxtweight)
        val weightPicker = view.findViewById<NumberPicker>(R.id.pickerWeight)

        auth = FirebaseAuth.getInstance();
        var userId = auth.currentUser?.uid.toString()

        weightPicker.minValue = 0
        weightPicker.maxValue = 300

        weightPicker.wrapSelectorWheel = true
        weightPicker.value = 60

        var updateData = FirebaseDatabase.getInstance()
                .getReference("USERS")
                .child(userId)

        next.setOnClickListener(View.OnClickListener {
            updateData.child("weight").setValue(weightPicker.value).addOnCompleteListener {
                task ->
                if (task.isSuccessful) {
                    Log.d("Ref:","Sukses")
                    startActivity(Intent(view.context,InsertProfileActivity::class.java))
                } else {
                    Log.d("Ref:",task.exception.toString())
                }
            }
        })
    }
}
