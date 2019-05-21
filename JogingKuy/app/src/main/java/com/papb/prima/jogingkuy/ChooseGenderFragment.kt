package com.papb.prima.jogingkuy


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.google.firebase.database.FirebaseDatabase


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ChooseGenderFragment : Fragment(), FragmentChangeListener{


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
        return inflater.inflate(R.layout.fragment_choose_gender, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val ImgMale = view.findViewById<ImageView>(R.id.imgMale)
        val ImgFemale = view.findViewById<ImageView>(R.id.imgFemale)

        var updateData = FirebaseDatabase.getInstance()
                .getReference("USERS")
                .child(RegisterActivity.registeredId);

        ImgFemale.setOnClickListener(View.OnClickListener {
            updateData.child("gender").setValue("female").addOnCompleteListener {
                task ->
                if (task.isSuccessful) {
                    Log.d("Ref:","Sukses")

                    val fr = SetAgeFragment()
                    ReplaceFragment(fr)
                } else {
                    Log.d("Ref:",task.exception.toString())
                }
            }
        })

        ImgMale.setOnClickListener(View.OnClickListener {
            updateData.child("gender").setValue("male").addOnCompleteListener {
                task ->
                if (task.isSuccessful) {
                    Log.d("Ref:","Sukses")

                    val fr = SetAgeFragment()
                    ReplaceFragment(fr)
                } else {
                    Log.d("Ref:",task.exception.toString())
                }
            }
        })
    }
}
