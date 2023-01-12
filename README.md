# MengNetwork
 ```
	dependencies {
	        implementation 'com.github.Lymengchun:MengNetwork:1.0.1'
	}
  ```
  
   # Implement
   
  ```
        mengNetwork.getInstance().newCall(GlobalVariable.getInstance().arData.get(0), GlobalVariable.getInstance().arData.get(1)+sharedPref.getString("id", FB_ID),new Callback(){

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        new AlertDialog.Builder(getContext())
//                                .setTitle("Error")
//                                .setMessage("Please try again?")
//                                .setPositiveButton("try again!", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        getVpStartApp();
//                                    }
//                                }).setNegativeButton("cancel",null).show();
                        getVpStartApp();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String data = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(data);
                        JSONObject jsonObject1  = new JSONObject(jsonObject.getString(GlobalVariable.getInstance().arData.get(4)));
                        JSONArray jsonArray = new JSONArray(jsonObject1.getString(GlobalVariable.getInstance().arData.get(10)));

                        ArrayList<String> appName = new ArrayList<String>();
                        ArrayList<String> imageLink = new ArrayList<String>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String app_name = object.getString(GlobalVariable.getInstance().arData.get(12));
                            String image_link = object.getString(GlobalVariable.getInstance().arData.get(14));

                            appName.add(app_name);
                            imageLink.add(image_link);
                            Log.d("Demo1","debug");
                        }


                        for (int i = 0; i < appName.size(); i++) {
                            ecoTechAppCardList.add(new EcoTechAppCardModel(imageLink.get(i),appName.get(i)+"\nApp"));
                            Log.d("Demo","debug");
                        }

                        getActivity().runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                // Stuff that updates the UI
                                ecoTechAppAdapter = new EcoTechAppAdapter(getContext(),ecoTechAppCardList,EcoTechAppFragment.this);
                                ecoTechAppRecyclerView.setAdapter(ecoTechAppAdapter);
                                if (progressDialog.isShowing())
                                    progressDialog.dismiss();
                            }
                        });


                        Log.d("Demo", "vpstart app List:" + jsonArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new AlertDialog.Builder(getContext())
                                        .setTitle("Error")
                                        .setMessage(e.getMessage())
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        }).setNegativeButton("No",null).show();
                            }
                        });
                    }


                }

            }


        });
