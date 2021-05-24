package myzhong.com.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import myzhong.com.R;

public class CalculatorFragment extends Fragment {

    private CalculatorViewModel mViewModel;
    private static final String REQUEST_PREFIX = "http://10.0.2.2:8080/Calculator/calculate?input=";
    private Gson gson;

    public static CalculatorFragment newInstance() {
        return new CalculatorFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.calculator_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CalculatorViewModel.class);

        gson = new Gson();

        Button button_0 = getActivity().findViewById(R.id.button_0);
        Button button_1 = getActivity().findViewById(R.id.button_1);
        Button button_2 = getActivity().findViewById(R.id.button_2);
        Button button_3 = getActivity().findViewById(R.id.button_3);
        Button button_4 = getActivity().findViewById(R.id.button_4);
        Button button_5 = getActivity().findViewById(R.id.button_5);
        Button button_6 = getActivity().findViewById(R.id.button_6);
        Button button_7 = getActivity().findViewById(R.id.button_7);
        Button button_8 = getActivity().findViewById(R.id.button_8);
        Button button_9 = getActivity().findViewById(R.id.button_9);

        button_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest("0");
            }
        });

        button_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest("1");
            }
        });

        button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest("2");
            }
        });

        button_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest("3");
            }
        });

        button_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest("4");
            }
        });

        button_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest("5");
            }
        });

        button_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest("6");
            }
        });

        button_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest("7");
            }
        });

        button_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest("8");
            }
        });

        button_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest("9");
            }
        });


        Button button_dot = getActivity().findViewById(R.id.button_dot);
        button_dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest(".");
            }
        });

        Button button_AC = getActivity().findViewById(R.id.button_AC);
        button_AC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetOperators();
                sendRequest("c");
            }
        });

        Button button_plus = getActivity().findViewById(R.id.button_plus);
        Button button_minus = getActivity().findViewById(R.id.button_minus);
        Button button_multiply = getActivity().findViewById(R.id.button_multiply);
        Button button_divide = getActivity().findViewById(R.id.button_divide);

        button_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest("p");
            }
        });

        button_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest("s");
            }
        });

        button_multiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest("m");
            }
        });

        button_divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest("d");
            }
        });


        Button button_equal = getActivity().findViewById(R.id.button_equal);
        button_equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest("e");
            }
        });

    }


    /**
     * reset all member variables back to default
     */
    private void clickAC(){
        resetOperators();
        sendRequest("c");
    }

    /**
     * Reset all operator buttons back to original background color
     */
    private void resetOperators(){
        Button button_plus = getActivity().findViewById(R.id.button_plus);
        Button button_minus = getActivity().findViewById(R.id.button_minus);
        Button button_multiply = getActivity().findViewById(R.id.button_multiply);
        Button button_divide = getActivity().findViewById(R.id.button_divide);

        button_plus.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.operator));
        button_minus.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.operator));
        button_multiply.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.operator));
        button_divide.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.operator));
    }

    private void sendRequest(String input){
        try {
            String requestURL = REQUEST_PREFIX + input;
            System.out.println("URL: " + requestURL);
            //CalculatorTask task = new CalculatorTask();
            //task.execute(requestURL);
            String response = new CalculatorTask()
                    .execute(requestURL).get();

            Log.i("response: ========", response);


            if(response == null || response.isEmpty()) {
                Toast.makeText(getActivity(), "Empty response from server",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            JsonObject result = gson.fromJson(response, JsonObject.class);
            String currentNumber = result.get("display").getAsString();
            boolean plus = result.get("plus").getAsBoolean();
            boolean subtract = result.get("subtract").getAsBoolean();
            boolean multiply = result.get("multiply").getAsBoolean();
            boolean divide = result.get("divide").getAsBoolean();

            TextView tvDisplay = getActivity().findViewById(R.id.calculator_display);
            tvDisplay.setText(currentNumber);

            highlightOperator(plus, subtract, multiply, divide);
        }
        catch(Exception e){
            Toast.makeText(getActivity(), "Error connecting to server",
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void highlightOperator(boolean plus, boolean subtract,
                                   boolean multiply, boolean divide){
        resetOperators();
        Button button = null;

        if(plus)
            button = getActivity().findViewById(R.id.button_plus);
        else if(subtract)
            button = getActivity().findViewById(R.id.button_minus);
        else if(multiply)
            button = getActivity().findViewById(R.id.button_multiply);
        else if(divide)
            button = getActivity().findViewById(R.id.button_divide);

        if(button != null)
            button.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.white));
    }

}