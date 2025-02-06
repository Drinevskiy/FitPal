package by.drinevskiy.fitpal.presentation.add

//import android.R
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.drinevskiy.fitpal.R
import by.drinevskiy.fitpal.databinding.FragmentAddBinding
import by.drinevskiy.fitpal.domain.model.FoodListItem
import by.drinevskiy.fitpal.domain.repository.FoodFakeRepository
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlin.random.Random


class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var adapter: FoodAdapter
    private val viewModel: AddViewModel by viewModels()

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

        adapter = FoodAdapter( {
            viewModel.likeFood(it) // Update the ViewModel
            adapter.data = viewModel.getFoodList() // Refresh the adapter data
            if(adapter.data.isEmpty()){
                binding.buttonSaveCart.isEnabled = false
            } else{
                binding.buttonSaveCart.isEnabled = true
            }
        },{
            viewModel.deleteFood(it) // Update the ViewModel
            adapter.data = viewModel.getFoodList() // Refresh the adapter data
            if(adapter.data.isEmpty()){
                binding.buttonSaveCart.isEnabled = false
            } else{
                binding.buttonSaveCart.isEnabled = true
            }
        })
        adapter.data = viewModel.getFoodList()
        binding.foodListRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.foodListRecycler.adapter = adapter
        if(adapter.data.isEmpty()){
            binding.buttonSaveCart.isEnabled = false
        } else{
            binding.buttonSaveCart.isEnabled = true
        }
        val dividerItemDecoration = DividerItemDecoration(binding.foodListRecycler.context, LinearLayoutManager.VERTICAL)
        binding.foodListRecycler.addItemDecoration(dividerItemDecoration)
        return root
    }

    private fun showAddItemDialog() {
        // Создайте макет для диалога
        val dialogView: View = layoutInflater.inflate(R.layout.dialog_add_product, null)

        // Создайте диалог
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setView(dialogView)
            .setTitle(R.string.add_product)
            .setPositiveButton(R.string.title_add_product) { dialog, which ->
                // Получите данные из EditText
                val editText1 = dialogView.findViewById<EditText>(R.id.editText1)
                val editText2 = dialogView.findViewById<EditText>(R.id.editText2)
                val editText3 = dialogView.findViewById<EditText>(R.id.editText3)
                val editText4 = dialogView.findViewById<EditText>(R.id.editText4)
                val editText5 = dialogView.findViewById<EditText>(R.id.editText5)
                val editText6 = dialogView.findViewById<EditText>(R.id.editText6)
                val editText7 = dialogView.findViewById<EditText>(R.id.editText7)
                viewModel.addFood(FoodListItem(
                    id = Random.nextInt(),
                    name = editText1.text.toString(),
                    weight = editText2.text.toString().toDouble(),
                    ccal = editText3.text.toString().toInt(),
                    protein = editText4.text.toString().toDouble(),
                    fat = editText5.text.toString().toDouble(),
                    carbons = editText6.text.toString().toDouble(),
                    cost = editText7.text.toString().toDouble(),
                    isLiked = false,
                ))
                adapter.data = viewModel.getFoodList() // Refresh the adapter data
                if(adapter.data.isEmpty()){
                    binding.buttonSaveCart.isEnabled = false
                } else{
                    binding.buttonSaveCart.isEnabled = true
                }

            }
            .setNegativeButton(R.string.cancel) { dialog, which -> dialog.dismiss() }
            .show()
//        val dialog = builder.create()
//        dialog.setOnShowListener { d: DialogInterface? ->
//            val cancelButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
//            cancelButton?.setTextColor(resources.getColor(R.color.md_theme_onTertiary_mediumContrast))
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}