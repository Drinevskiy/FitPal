package by.drinevskiy.fitpal.presentation.add

//import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import by.drinevskiy.fitpal.R
import by.drinevskiy.fitpal.databinding.FragmentAddBinding
import by.drinevskiy.fitpal.domain.model.FoodListItem
import by.drinevskiy.fitpal.domain.model.PurchaseListItem
import by.drinevskiy.fitpal.presentation.kitchen.KitchenViewModel
import by.drinevskiy.fitpal.presentation.utils.launchUntilPaused
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var adapter: FoodAdapter
    private val viewModel: AddViewModel by viewModels()
    private val kitchenViewModel: KitchenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.buttonAddProduct.setOnClickListener {
            showAddItemDialog()
        }
        binding.buttonSaveCart.setOnClickListener {
            kitchenViewModel.addPurchase(PurchaseListItem(foods = viewModel.uiState.value.foodList))
            Snackbar.make(binding.root, "Продукты добавлены", Snackbar.LENGTH_INDEFINITE)
                .setDuration(1500)
                .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
                .setAnchorView(R.id.nav_view)
                .show()
            viewModel.removeAllFood()
        }

        adapter = FoodAdapter{
            viewModel.likeFood(it) // Update the ViewModel
            binding.buttonSaveCart.isEnabled = adapter.data.isNotEmpty()
        }
        lifecycleScope.launchUntilPaused(this){
            viewModel.uiState.collect{
                adapter.data = it.foodList
                binding.buttonSaveCart.isEnabled = adapter.data.isNotEmpty()
            }
        }
        binding.foodListRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.foodListRecycler.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(binding.foodListRecycler.context, LinearLayoutManager.VERTICAL)
        binding.foodListRecycler.addItemDecoration(dividerItemDecoration)

        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback{
            viewModel.removeFood(adapter.data[it]) // Update the ViewModel
        })
        itemTouchHelper.attachToRecyclerView(binding.foodListRecycler)
        return root
    }

    private fun showAddItemDialog() {
        // Создайте макет для диалога
        val dialogView: View = layoutInflater.inflate(R.layout.dialog_add_product, null)

        // Создайте диалог
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setView(dialogView)
            .setTitle(R.string.add_product)
            .setPositiveButton(R.string.title_add_product, null)
            .setNegativeButton(R.string.cancel) { dialog, which -> dialog.dismiss() }
        val dialog = builder.create()
        dialog.setOnShowListener {
            val editText1 = dialogView.findViewById<TextInputLayout>(R.id.editText1)
            val editText2 = dialogView.findViewById<TextInputLayout>(R.id.editText2)
            val editText3 = dialogView.findViewById<TextInputLayout>(R.id.editText3)
            val editText4 = dialogView.findViewById<TextInputLayout>(R.id.editText4)
            val editText5 = dialogView.findViewById<TextInputLayout>(R.id.editText5)
            val editText6 = dialogView.findViewById<TextInputLayout>(R.id.editText6)
            val editText7 = dialogView.findViewById<TextInputLayout>(R.id.editText7)
            val editTexts = listOf(editText1, editText2, editText3, editText4, editText5, editText6, editText7)
            dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
            editText1.editText?.requestFocus()
//            editText1.editText?.setSelection(0)

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                // Получите данные из EditText
                var valid = true

                for (editText in editTexts) {
                    editText.editText?.doOnTextChanged { _, _, _, _ ->
                        editText.error = null
                    }
                    if (editText.editText?.text?.isEmpty() == true) {
                        editText.error = getString(R.string.enter_value_error_message)
                        valid = false
                    } else {
                        editText.error = null
                    }
                }
                if(valid) {
                    viewModel.addFood(
                        FoodListItem(
                            name = editText1.editText?.text.toString(),
                            weight = editText2.editText?.text.toString().toDouble(),
                            ccal = editText3.editText?.text.toString().toInt(),
                            protein = editText4.editText?.text.toString().toDouble(),
                            fat = editText5.editText?.text.toString().toDouble(),
                            carbons = editText6.editText?.text.toString().toDouble(),
                            cost = editText7.editText?.text.toString().toDouble(),
                            isLiked = false,
                        )
                    )
                    dialog.dismiss()
                }
            }
        }
        dialog.show()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}