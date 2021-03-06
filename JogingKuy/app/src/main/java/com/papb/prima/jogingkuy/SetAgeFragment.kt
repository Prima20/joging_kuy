package com.papb.prima.jogingkuy


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
class SetAgeFragment : Fragment() , FragmentChangeListener{

    private lateinit var auth: FirebaseAuth

    //Implement interface
    override fun ReplaceFragment(fragment: Fragment?) {
        val fragmentMan = fragmentManager
        val fragmentTrans = fragmentMan?.beginTransaction()
        fragmentTrans?.replace(R.id.your_placeholder,fragment,fragmentMan.toString())
        fragmentTrans?.addToBackStack(fragment.toString())
        fragmentTrans?.commit()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_set_age, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val next = view.findViewById<ImageView>(R.id.nxtAge)
        val agePicker = view.findViewById<NumberPicker>(R.id.pickerAge)

        auth = FirebaseAuth.getInstance();
        var userId = auth.currentUser?.uid.toString()

        agePicker.minValue = 0
        agePicker.maxValue = 100

        agePicker.wrapSelectorWheel = true
        agePicker.value = 19

        var updateData = FirebaseDatabase.getInstance()
                .getReference("USERS")
                .child(userId);

        next.setOnClickListener(View.OnClickListener {
            updateData.child("age").setValue(agePicker.value).addOnCompleteListener {
                task ->
                if (task.isSuccessful) {
                    Log.d("Ref:","Sukses")

                    val fr = SetHeightFragment()
                    ReplaceFragment(fr)
                } else {
                    Log.d("Ref:",task.exception.toString())
                }
            }
        })
    }
}
