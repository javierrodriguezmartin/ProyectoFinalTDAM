package com.example.reachthegym.transiciones;

import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.TransitionSet;

public class TransicionCard1 extends TransitionSet {

    public TransicionCard1 (){
        setOrdering(ORDERING_TOGETHER);
        addTransition(new ChangeBounds()).
                addTransition(new ChangeBounds()).
                addTransition(new ChangeImageTransform());

    }

}
