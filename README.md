# InteractivePlayerView
[![Join the chat at https://gitter.im/iammert/InteractivePlayerView](https://img.shields.io/badge/GITTER-join%20chat-green.svg)](https://gitter.im/iammert/InteractivePlayerView)

Custom android music player view.

# Screen

<img src="https://raw.githubusercontent.com/iammert/InteractivePlayerView/master/art/art.png"/>

Check it on [youtube](https://www.youtube.com/watch?v=9cN5PCjUioM)

# Usage(XML)

Define it in your xml file.

```xml
 <co.mobiwise.library.InteractivePlayerView
            android:id="@+id/ipv"
            android:layout_width="230dp"
            android:layout_height="230dp"
            app:imageCover="@drawable/imagetest"
            app:emptyColor="#aaffffff"
            app:loadedColor="#fff44336"
            app:selectedAction1="@drawable/shuffle_selected"
            app:selectedAction2="@drawable/like_selected"
            app:selectedAction3="@drawable/replay_selected"
            app:unselectedAction1="@drawable/shuffle_unselected"
            app:unselectedAction2="@drawable/like_unselected"
            app:unselectedAction3="@drawable/replay_unselected" />
```

**IMPORTANT** : I designed shuffle, like and replay icons for my demo app. You can create your by using
[Flat Icon](http://flaticon.com) website. Or, if you want to use mine instead of creating new icon set, then you can download my action set from [here](https://github.com/iammert/InteractivePlayerView/blob/master/demoIcons.zip).


Find view and set necessary values.

```java
InteractivePlayerView ipv = (InteractivePlayerView) findViewById(R.id.ipv);
ipv.setMax(123); // music dutation in seconds.
ipv.setOnActionClickedListener(new OnActionClickedListener() {
            @Override
            public void onActionClicked(int id) {
                switch (id){
                    case 1:
                        //Called when 1. action is clicked.
                        break;
                    case 2:
                        //Called when 2. action is clicked.
                        break;
                    case 3:
                        //Called when 3. action is clicked.
                        break;
                    default:
                        break;
                }
            }
        });
```

Start and stop depends on your player.

```java
ipv.start();
ipv.stop();
```

# Usage (Java)

```java
ipv.setCoverDrawable(R.drawable.imagetest);
ipv.setActionOneImage(R.drawable.shuffle_selected, R.drawable.shuffle_unselected);
ipv.setActionTwoImage(R.drawable.like_selected, R.drawable.like_unselected);
ipv.setActionThreeImage(R.drawable.replay_selected, R.drawable.replay_unselected);
ipv.setProgressEmptyColor(Color.GRAY);
ipv.setProgressEmptyColor(Color.BLACK);
```

# Useful methods

```java
//Loads image from url (By Picasso)
ipv.setCoverURL("http://abc.xyz/1.png");
```

```java
//edit your current progress
ipv.setProgress(12);
int currentProgress = ipv.getProgress();
```

```java
//Check if any action selected or not. Or edit.
boolean isSelected = ipv.isAction1Selected();
ipv.setAction1Selected(true);
```

```java
//Check if ipv is playing
ipv.isPlaying();
```

# Import

Project build.gradle

```
repositories {
    maven {
        url "https://jitpack.io"
    }
}
```

Module build.gradle
```
dependencies {
	   compile 'com.github.iammert:InteractivePlayerView:8fd08a6d8b'
}
```

# Design

[Here is original design](https://www.pinterest.com/pin/400187116866664878/)

# Library used

[Picasso by Square](http://square.github.io/picasso/)


License
--------


    Copyright 2015 Mert Şimşek.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


