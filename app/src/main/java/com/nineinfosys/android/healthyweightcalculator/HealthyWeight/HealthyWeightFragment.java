package com.nineinfosys.android.healthyweightcalculator.HealthyWeight;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.nineinfosys.android.healthyweightcalculator.MainActivityDrawer;
import com.nineinfosys.android.healthyweightcalculator.R;


public class HealthyWeightFragment extends Fragment {
    EditText editTextHeight,edittextfeet,edittextInch;
    Button buttonCalculate,buttonMoreInfo;
    ImageView imageViewHeight;
    private RadioGroup radioGroupHeight;
    private RadioButton radioButtonHeight;
    TextView textViewHealthyWeight;
    WebView Introwebview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_main_healthyweight, null);

        MobileAds.initialize(getActivity(), getString(R.string.ads_app_id));
        AdView mAdView = (AdView) v.findViewById(R.id.adViewMainPagehealthy);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        ((MainActivityDrawer) getActivity()).toolbar.setTitle("Healthy Weight");
        editTextHeight = (EditText) v.findViewById(R.id.editTextHeight);
        edittextfeet = (EditText) v.findViewById(R.id.edittextFeet);
        edittextInch = (EditText) v.findViewById(R.id.edittextInch);
        imageViewHeight = (ImageView) v.findViewById(R.id.imageViewHeight);
        buttonCalculate = (Button) v.findViewById(R.id.buttonCalculate);
        buttonMoreInfo = (Button) v.findViewById(R.id.buttonMoreInfo);
        textViewHealthyWeight = (TextView) v.findViewById(R.id.textViewHealthyWeight);

        buttonMoreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //alert Dialog Declaration For More Infomation
                final LayoutInflater inflaterMoreInfo = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View alertLayoutMoreInfo = inflaterMoreInfo.inflate(R.layout.info_webview, null);
                final AlertDialog.Builder alertDialogBuilderMoreInfo = new AlertDialog.Builder(getActivity());
                alertDialogBuilderMoreInfo.setTitle("More Info:");
                Introwebview = (WebView) alertLayoutMoreInfo.findViewById(R.id.webViewinfo);
                WebSettings IntroWebSettings = Introwebview.getSettings();
                IntroWebSettings.setBuiltInZoomControls(true);
                IntroWebSettings.setJavaScriptEnabled(true);
                IntroWebSettings.setUseWideViewPort(true);
                IntroWebSettings.setLoadWithOverviewMode(true);
                Introwebview.setWebViewClient(new WebViewClient());
                Introwebview.loadUrl("file:///android_res/raw/healthyweight.html");
                alertDialogBuilderMoreInfo.setView(alertLayoutMoreInfo);
                final AlertDialog alertDialogMoreInfo = alertDialogBuilderMoreInfo.create();
                alertDialogMoreInfo.show();
            }
        });



        //alert Dialog Declaration for Height
        final LayoutInflater inflaterHeight = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View alertLayoutHeight  = inflaterHeight.inflate(R.layout.dialogheight, null);
        final AlertDialog.Builder alertDialogBuilderHeight = new AlertDialog.Builder(getActivity());
        alertDialogBuilderHeight.setTitle("Height In :");
        radioGroupHeight = (RadioGroup) alertLayoutHeight.findViewById(R.id.radioHeight);
        alertDialogBuilderHeight.setView(alertLayoutHeight);
        final AlertDialog alertDialogHeight = alertDialogBuilderHeight.create();

        //on Alert Radio Button height On click listener
        imageViewHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vgender) {
                alertDialogHeight.show();
                radioGroupHeight.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        alertDialogHeight.cancel();
                        radioButtonHeight = (RadioButton) alertLayoutHeight.findViewById(radioGroup.getCheckedRadioButtonId());
                        // Toast.makeText(getApplication(), heightValue, Toast.LENGTH_SHORT).show();
                        if (radioButtonHeight.getText().toString().trim().equals("CM")) {
                            editTextHeight.setVisibility(View.VISIBLE);
                            edittextfeet.setVisibility(View.GONE);
                            edittextInch.setVisibility(View.GONE);
                            imageViewHeight.setImageResource(R.drawable.btn_cm);


                        } else {
                            editTextHeight.setVisibility(View.GONE);
                            edittextfeet.setVisibility(View.VISIBLE);
                            edittextInch.setVisibility(View.VISIBLE);
                            imageViewHeight.setImageResource(R.drawable.btn_ft_in);
                        }

                    }

                });

            }
        });
        //Calculating BMR
        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //for hiding keyboard
                InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                HealthyWeightCalculator healthyWeightCalculator=new HealthyWeightCalculator();
                if(radioGroupHeight.getCheckedRadioButtonId() == -1){
                    if(editTextHeight.getText().toString().trim().equals("")){
                        editTextHeight.setError("Enter Height In CM");
                    }else{
                        String healthyweightInKG=healthyWeightCalculator.HeightInCM(Float.parseFloat(editTextHeight.getText().toString().trim()));
                        textViewHealthyWeight.setText(healthyweightInKG);
                    }
                }else{
                    if(radioButtonHeight.getText().toString().trim().equals("CM")){
                        if(editTextHeight.getText().toString().trim().equals("")){
                            editTextHeight.setError("Enter Height In CM");
                        }else{
                            String healthyweightInKG=healthyWeightCalculator.HeightInCM(Float.parseFloat(editTextHeight.getText().toString().trim()));
                            textViewHealthyWeight.setText(healthyweightInKG);
                        }

                    }
                    else{
                        if(edittextfeet.getText().toString().trim().equals("")){
                            edittextfeet.setError("Enter Height In Feet");
                        }else if(edittextInch.getText().toString().trim().equals("")){
                            edittextInch.setError("Enter Height In Inch or Enter 0");
                        }else{
                            String healthyweightInLBS=healthyWeightCalculator.HeightInFTAndIn(Float.parseFloat(edittextfeet.getText().toString().trim()),Float.parseFloat(edittextInch.getText().toString().trim()));
                            textViewHealthyWeight.setText(healthyweightInLBS);
                        }
                    }

                }
            }
        });
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        return v;
    }
    public class WebViewClient extends android.webkit.WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }
    }
}
