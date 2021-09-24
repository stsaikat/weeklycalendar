package com.example.calendar.screen.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import com.example.calendar.R
import com.example.calendar.common.AppConsts
import com.example.calendar.common.getCreateEventDialog
import com.example.calendar.common.getTotalDayInMonth
import com.example.calendar.databinding.ActivityLogInBinding
import com.example.calendar.databinding.ActivityMainBinding
import com.example.calendar.databinding.CreateEventDialogLayoutBinding
import com.example.calendar.datamodel.Event
import com.example.calendar.datamodel.User
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dialogBinding: CreateEventDialogLayoutBinding
    private lateinit var user: User
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        dialogBinding = CreateEventDialogLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("xyz", "onCreate: ${viewModel.startDate}")

        user = Gson().fromJson(
            intent.getStringExtra(AppConsts.USER),
            User::class.java
        )

        setDate()

        val firstAdapter = Adapter(ArrayList())
        val secondAdapter = Adapter(ArrayList())
        val thirdAdapter = Adapter(ArrayList())
        val fourthAdapter = Adapter(ArrayList())
        val fifthAdapter = Adapter(ArrayList())
        val sixthAdapter = Adapter(ArrayList())
        val seventhAdapter = Adapter(ArrayList())

        binding.first.rvSingleDay.adapter = firstAdapter
        binding.second.rvSingleDay.adapter = secondAdapter
        binding.third.rvSingleDay.adapter = thirdAdapter
        binding.fourth.rvSingleDay.adapter = fourthAdapter
        binding.fifth.rvSingleDay.adapter = fifthAdapter
        binding.sixth.rvSingleDay.adapter = sixthAdapter
        binding.seventh.rvSingleDay.adapter = seventhAdapter

        viewModel.firstList.observe(this,{
            firstAdapter.setNewDataSet(it)
        })
        viewModel.secondList.observe(this,{
            secondAdapter.setNewDataSet(it)
        })
        viewModel.thirdList.observe(this,{
            thirdAdapter.setNewDataSet(it)
        })
        viewModel.fourthList.observe(this,{
            fourthAdapter.setNewDataSet(it)
        })
        viewModel.fifthList.observe(this,{
            fifthAdapter.setNewDataSet(it)
        })
        viewModel.sixthList.observe(this,{
            sixthAdapter.setNewDataSet(it)
        })
        viewModel.seventhList.observe(this,{
            seventhAdapter.setNewDataSet(it)
        })

        setAddButtons()

        binding.next.setOnClickListener {
            viewModel.startDate += 7
            setDate()
            viewModel.loadData()
        }

        binding.prev.setOnClickListener {
            viewModel.startDate -= 7
            setDate()
            viewModel.loadData()
        }
    }

    private fun setAddButtons(){
        binding.first.create.setOnClickListener {
            val dialogBinding = CreateEventDialogLayoutBinding.inflate(layoutInflater)
            val dialog = MaterialAlertDialogBuilder(this)
                .setView(dialogBinding.root)
                .show()

            dialogBinding.ok.setOnClickListener {
                dialog.dismiss()
                viewModel.addEvent(
                    Event(
                        dialogBinding.etTitle.text.toString(),
                        viewModel.startDate,
                        System.currentTimeMillis().toString(),
                        dialogBinding.etNote.text.toString()
                    ),
                    viewModel.startDate
                )
            }

            dialogBinding.cancel.setOnClickListener {
                dialog.dismiss()
            }
        }

        binding.second.create.setOnClickListener {
            val dialogBinding = CreateEventDialogLayoutBinding.inflate(layoutInflater)
            val dialog = MaterialAlertDialogBuilder(this)
                .setView(dialogBinding.root)
                .show()

            dialogBinding.ok.setOnClickListener {
                dialog.dismiss()
                viewModel.addEvent(
                    Event(
                        dialogBinding.etTitle.text.toString(),
                        viewModel.startDate+1,
                        System.currentTimeMillis().toString(),
                        dialogBinding.etNote.text.toString()
                    ),
                    viewModel.startDate+1
                )
            }

            dialogBinding.cancel.setOnClickListener {
                dialog.dismiss()
            }
        }

        binding.third.create.setOnClickListener {
            val dialogBinding = CreateEventDialogLayoutBinding.inflate(layoutInflater)
            val dialog = MaterialAlertDialogBuilder(this)
                .setView(dialogBinding.root)
                .show()

            dialogBinding.ok.setOnClickListener {
                dialog.dismiss()
                viewModel.addEvent(
                    Event(
                        dialogBinding.etTitle.text.toString(),
                        viewModel.startDate+2,
                        System.currentTimeMillis().toString(),
                        dialogBinding.etNote.text.toString()
                    ),
                    viewModel.startDate+2
                )
            }

            dialogBinding.cancel.setOnClickListener {
                dialog.dismiss()
            }
        }

        binding.fourth.create.setOnClickListener {
            val dialogBinding = CreateEventDialogLayoutBinding.inflate(layoutInflater)
            val dialog = MaterialAlertDialogBuilder(this)
                .setView(dialogBinding.root)
                .show()

            dialogBinding.ok.setOnClickListener {
                dialog.dismiss()
                viewModel.addEvent(
                    Event(
                        dialogBinding.etTitle.text.toString(),
                        viewModel.startDate+2+1,
                        System.currentTimeMillis().toString(),
                        dialogBinding.etNote.text.toString()
                    ),
                    viewModel.startDate+2+1
                )
            }

            dialogBinding.cancel.setOnClickListener {
                dialog.dismiss()
            }
        }

        binding.fifth.create.setOnClickListener {
            val dialogBinding = CreateEventDialogLayoutBinding.inflate(layoutInflater)
            val dialog = MaterialAlertDialogBuilder(this)
                .setView(dialogBinding.root)
                .show()

            dialogBinding.ok.setOnClickListener {
                dialog.dismiss()
                viewModel.addEvent(
                    Event(
                        dialogBinding.etTitle.text.toString(),
                        viewModel.startDate+4,
                        System.currentTimeMillis().toString(),
                        dialogBinding.etNote.text.toString()
                    ),
                    viewModel.startDate+4
                )
            }

            dialogBinding.cancel.setOnClickListener {
                dialog.dismiss()
            }
        }

        binding.sixth.create.setOnClickListener {
            val dialogBinding = CreateEventDialogLayoutBinding.inflate(layoutInflater)
            val dialog = MaterialAlertDialogBuilder(this)
                .setView(dialogBinding.root)
                .show()

            dialogBinding.ok.setOnClickListener {
                dialog.dismiss()
                viewModel.addEvent(
                    Event(
                        dialogBinding.etTitle.text.toString(),
                        viewModel.startDate+5,
                        System.currentTimeMillis().toString(),
                        dialogBinding.etNote.text.toString()
                    ),
                    viewModel.startDate+5
                )
            }

            dialogBinding.cancel.setOnClickListener {
                dialog.dismiss()
            }
        }

        binding.seventh.create.setOnClickListener {
            val dialogBinding = CreateEventDialogLayoutBinding.inflate(layoutInflater)
            val dialog = MaterialAlertDialogBuilder(this)
                .setView(dialogBinding.root)
                .show()

            dialogBinding.ok.setOnClickListener {
                dialog.dismiss()
                viewModel.addEvent(
                    Event(
                        dialogBinding.etTitle.text.toString(),
                        viewModel.startDate+6,
                        System.currentTimeMillis().toString(),
                        dialogBinding.etNote.text.toString()
                    ),
                    viewModel.startDate+6
                )
            }

            dialogBinding.cancel.setOnClickListener {
                dialog.dismiss()
            }
        }
    }

    private fun setDate() {
        val startDate = viewModel.startDate
        val dayInMonth = getTotalDayInMonth((startDate/100)%100)
        val day = startDate%100
        binding.first.date.text = (day%dayInMonth + 1).toString()
        binding.second.date.text = ((day+1)%dayInMonth + 1).toString()
        binding.third.date.text = ((day+2)%dayInMonth + 1).toString()
        binding.fourth.date.text = ((day+1+2)%dayInMonth + 1).toString()
        binding.fifth.date.text = ((day+4)%dayInMonth + 1).toString()
        binding.sixth.date.text = ((day+5)%dayInMonth + 1).toString()
        binding.seventh.date.text = ((day+6)%dayInMonth + 1).toString()
    }
}