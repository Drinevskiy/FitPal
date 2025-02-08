package by.drinevskiy.fitpal.presentation.add

//import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import by.drinevskiy.fitpal.R
import by.drinevskiy.fitpal.databinding.FragmentAddBinding
import by.drinevskiy.fitpal.domain.model.FoodListItem
import by.drinevskiy.fitpal.domain.model.OpenFoodItem
import by.drinevskiy.fitpal.domain.model.PurchaseListItem
import by.drinevskiy.fitpal.presentation.kitchen.KitchenViewModel
import by.drinevskiy.fitpal.presentation.utils.launchUntilPaused
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private var currentDialog: AlertDialog? = null
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
        binding.buttonScanProduct.setOnClickListener {
            val options = GmsBarcodeScannerOptions.Builder()
                .enableAutoZoom() // available on 16.1.0 and higher
                .build()
            val scanner = GmsBarcodeScanning.getClient(requireContext(), options)
            scanner.startScan()
                .addOnSuccessListener { barcode ->
                    // Task completed successfully
                    viewModel.loadProductInfoByBarcode(barcode.displayValue)
                    var dialogShown = false
//                    lifecycleScope.launchUntilPaused(this){
                    viewLifecycleOwner.lifecycleScope.launch {
                        repeatOnLifecycle(Lifecycle.State.STARTED) {
//                        viewModel.uiState.collect { uiState ->
//                            uiState.openFoodItem?.let { openFoodItem ->
//                                if (!dialogShown) {
//                                    dialogShown = true
//
//                                    showAddItemDialog(openFoodItem)
//                                }
//                            } ?: run {
//                                Toast.makeText(requireContext(), "Ошибка сканирования. \nВозможно, штрих-кода нет в базе", Toast.LENGTH_LONG).show()
//
//                                // Сброс флага, если openFoodItem становится null
//                                dialogShown = false
//                            }
//                        }
                            //Гарантируем единственный вызов
                            viewModel.uiState.map { it.openFoodItem }.distinctUntilChanged()
                                .collect { item ->
//                            showAddItemDialog(item)
                                    Log.i("AddFragment", "showDialog" + item.toString())
                                    currentDialog?.dismiss()
                                    item?.let {
                                        currentDialog = showAddItemDialog(item)
                                    }
//                                showAddItemDialog()
//                                Snackbar.make(requireActivity().window.decorView.findViewById<View>(android.R.id.content), "Ошибка сканирования. \nВозможно, штрих-кода нет в базе", Snackbar.LENGTH_INDEFINITE)
//                                    .setDuration(1500)
//                                    .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
//                                    .setTextMaxLines(2)
////                                    .setAnchorView(R.id.nav_view)
//                                    .show()
//                            }
//                            uiState.openFoodItem?.let { openFoodItem ->
//                                showAddItemDialog(openFoodItem)
//                            }
//                            return@collect // Завершаем дальнейшую обработку
                                }
                        }
                    }
//                    showAddItemDialog(viewModel.uiState.value.openFoodItem)
                }
                .addOnCanceledListener {
                    Snackbar.make(binding.root, "Сканирование отменено", Snackbar.LENGTH_INDEFINITE)
                        .setDuration(1500)
                        .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
                        .setAnchorView(R.id.nav_view)
                        .show()
                }
                .addOnFailureListener { e ->
                    Snackbar.make(binding.root, "Ошибка сканирования. Попробуйте снова", Snackbar.LENGTH_INDEFINITE)
                        .setDuration(1500)
                        .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
                        .setAnchorView(R.id.nav_view)
                        .show()
                }

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
//        lifecycleScope.launchUntilPaused(this){
//            viewModel.uiState.collect{
//                Log.i("AddFragment", "Food list updated: ${it.foodList}")
//                adapter.data = it.foodList
//                binding.buttonSaveCart.isEnabled = adapter.data.isNotEmpty()
//
//            }
//        }
        viewLifecycleOwner.lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    Log.i("AddFragment", "Food list updated: ${it.foodList}")
                    adapter.data = it.foodList
                    binding.buttonSaveCart.isEnabled = adapter.data.isNotEmpty()

                }
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

    private fun showAddItemDialog(openFoodItem: OpenFoodItem? = null): AlertDialog {
        // Создайте макет для диалога
        val dialogView: View = layoutInflater.inflate(R.layout.dialog_add_product, null)

        // Создайте диалог
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setView(dialogView)
            .setTitle(R.string.add_product)
            .setPositiveButton(R.string.title_add_product, null)
            .setNegativeButton(R.string.cancel) { dialog, which -> {
                dialog.dismiss()
            } }
        val dialog = builder.create()
        Log.i("AddFragment", viewModel.uiState.value.toString())
        dialog.setOnShowListener {
            val editText1 = dialogView.findViewById<TextInputLayout>(R.id.editText1)
            val editText2 = dialogView.findViewById<TextInputLayout>(R.id.editText2)
            val editText3 = dialogView.findViewById<TextInputLayout>(R.id.editText3)
            val editText4 = dialogView.findViewById<TextInputLayout>(R.id.editText4)
            val editText5 = dialogView.findViewById<TextInputLayout>(R.id.editText5)
            val editText6 = dialogView.findViewById<TextInputLayout>(R.id.editText6)
            val editText7 = dialogView.findViewById<TextInputLayout>(R.id.editText7)
            openFoodItem?.let { item ->
                    editText1.editText?.setText(item.name)
                    editText2.editText?.setText(item.weight.toString())
                    editText3.editText?.setText(item.kcal.toString())
                    editText4.editText?.setText(item.protein.toString())
                    editText5.editText?.setText(item.fat.toString())
                    editText6.editText?.setText(item.carbons.toString())
                    dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
                    editText7.editText?.requestFocus()
            }
            val editTexts = listOf(editText1, editText2, editText3, editText4, editText5, editText6, editText7)
            if (openFoodItem == null) {
                dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
                editText1.editText?.requestFocus()
            }
//            editText1.editText?.setSelection(0)

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                // Получите данные из EditText
                var valid = true
                Log.i("AddFragment", viewModel.uiState.value.toString())

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
                            kcal = editText3.editText?.text.toString().toInt(),
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
        return dialog
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

