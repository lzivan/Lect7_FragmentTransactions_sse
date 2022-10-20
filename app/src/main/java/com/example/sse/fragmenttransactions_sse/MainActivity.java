package com.example.sse.fragmenttransactions_sse;

//import android.app.Activity;
//import android.app.FragmentManager;
//import android.app.FragmentTransaction;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity{

//Step-By-Step, Fragment Transactions

    //Two basic ways of working with fragments.
    //
    //1. Just include them in the Activity's layout.
    //
    //2. Instantatiate and work with them in code.
    // in code you have much more control.

    //3. create objects to reference the views, including fragments.
private
    Frag_One  f1;
    Frag_Two  f2;
    Frag_Three  f3;

    FragmentManager fm;  // we will need this later.

    private Button btnFrag1;
    private Button btnFrag2;
    private Button btnFrag3;
    private LinearLayout FragLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    //4. get references for our views.

        btnFrag1 = (Button) findViewById(R.id.btnFrag1);
        btnFrag2 = (Button) findViewById(R.id.btnFrag2);
        btnFrag3 = (Button) findViewById(R.id.btnFrag3);
        FragLayout = (LinearLayout) findViewById(R.id.FragLayout);

//        f1 = (Frag_One) findViewById(R.id.frag1);  //Q: Why won't this work for fragments?  Does the fragment even exist in R.java?
          // Because fragments have to be found in fragment manager, they don't exist in R.java

    //5a.  We actually have to create the fragments ourselves.  We left R behind when we took control of rendering.
        f1 = new Frag_One();
        f2 = new Frag_Two();
        f3 = new Frag_Three();

    //5b. Grab a reference to the Activity's Fragment Manager, Every Activity has one!
       fm = getFragmentManager ();  //that was easy.
//         fm = getSupportFragmentManager();  // **When would you use this instead??
// A: If we are using API >= 11, then use getFragmentManager() and while using Support Package you have to use getSupportFragmentManager()


    //5c. Now we can "plop" fragment(s) into our container.
    // starting with fragment 1 (f1)
        FragmentTransaction ft = fm.beginTransaction ();  //Create a reference to a fragment transaction.
        ft.add(R.id.FragLayout, f1, "tag1");  //now we have added our fragement to our Activity programmatically.  The other fragments exist, but have not been added yet.
        ft.addToBackStack ("myFrag1");  //why do we do this?
        // Add this transaction to the back stack. This means that the transaction will be remembered after it is committed, and will reverse its operation when later popped off the stack.


        ft.commit ();  //don't forget to commit your changes.  It is a transaction after all.

    btnFrag1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showFrag1();
        }
    });

        btnFrag2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFrag2();
            }
        });

        btnFrag3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFrag3();
            }
        });

    }

public void showFrag1() {
        if (f1 == null) { // check whether f1 is null
            f1 = new Frag_One();
            Log.w("MainActivity","f1 created"+ R.layout.activity_main);
        }else {
            f1 = (Frag_One) fm.findFragmentByTag("tag1");
            Log.w("MainActivity","Hello"+ R.layout.activity_main); //what should we do if f1 doesn't exist anymore?  How do we check and how do we fix?
        }
    FragmentTransaction ft = fm.beginTransaction (); //Create a reference to a fragment transaction.
    ft.replace(R.id.FragLayout, f1); // use replace to show up f1
    if (f1.isDetached() == true){ // check f1 whether it's detached, if it is, create a new one and replace it to ft, and attach it
        f1 = new Frag_One();
        ft.replace(R.id.FragLayout, f1);
        ft.attach(f1);
        Log.w("MainActivity","f1 attached"+ R.layout.activity_main); //what should we do if f1 doesn't exist anymore?  How do we check and how do we fix?

    }
    ft.hide(f2);
    ft.hide(f3);
    ft.show(f1);

 //why does this not *always* crash?
    ft.commit();
}

    public void showFrag2() {

        if (f2 == null) {
            // if f2 is null, create a new reference
            f2 = new Frag_Two();
            FragmentTransaction ft = fm.beginTransaction ();
            if (f2.isAdded() == false) { // if f2 isn't added, add f2 plus tag
                ft.add(R.id.FragLayout, f2, "tag2");
                Log.w("MainActivity","f2 created"+ R.layout.activity_main); //what should we do if f1 doesn't exist anymore?  How do we check and how do we fix?

            }
            ft.replace(R.id.FragLayout, f2); // use replace to show f2
            ft.addToBackStack ("myFrag2");
            ft.commit();

        }else {



            FragmentTransaction ft = fm.beginTransaction(); //Create a reference to a fragment transaction and start the transaction.
            if (f2.isAdded() == true) {
                f2 = (Frag_Two) fm.findFragmentByTag("tag2");
            }else{
                ft.add(R.id.FragLayout, f2, "tag2");

            }
            ft.replace(R.id.FragLayout, f2);
            ft.addToBackStack("myFrag1");  //Q: What is the back stack and why do we do this? _______________
            if (f2.isHidden() == true){ // check f2 whether it's hidden
                ft.show(f2);
            }

            if (f2.isDetached() == true){ // check f2 whether it's detached, if it is, create a new one and replace it to ft, and attach it
                f2 = new Frag_Two();
                ft.replace(R.id.FragLayout, f2);
                ft.attach(f2);
                Log.w("MainActivity","f2 attached"+ R.layout.activity_main); //what should we do if f1 doesn't exist anymore?  How do we check and how do we fix?

            }
            ft.addToBackStack ("myFrag2");
            ft.commit();
        }
    }


    public void showFrag3() {
        // check whether f1,2,3 are null, if they are, create new references
        if (f3 == null)
            f3 = new Frag_Three();
        if (f2 == null)
            f2 = new Frag_Two();
        if (f1 == null)
            f1 = new Frag_One();

        FragmentTransaction ft = fm.beginTransaction (); //Create a reference to a fragment transaction.
        ft.replace(R.id.FragLayout, f3); // Use replace to make f3 show up on ft

        ft.detach(f1);//what would happen if f1, f2, or f3 were null?  how would we check and fix this?
        ft.detach(f2);
        ft.attach(f3);

        if (f3.isHidden() == true){  // check whether f3 is hidden, if it is, make it show up
            ft.show(f3);
        }
        ft.addToBackStack ("myFrag3");
        ft.commit();
    }
}
