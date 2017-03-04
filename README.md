# Pre-work - JustDoIt
CodePath Android Bootcamp 2017 Submission

**JustDoIt** is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: **Leo Kim**

Time spent: **3** hours spent in total

## User Stories

The following **required** functionality is completed:

* [X] User can **successfully add and remove items** from the todo list
* [X] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [X] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [X] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* [ ] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [ ] Add support for completion due dates for todo items (and display within listview item)
* [ ] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items
* [ ] Add support for selecting the priority of each todo item (and display in listview item)
* [ ] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:

* [ ] Notification when deadline approches for to do item

## Video Walkthrough 

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/q9Gy7On.gifv' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

The biggest challenges I have faced while working on this project was restructuring the app from one stage to next stage. In my very first iteration, I had followed the tutorial made by CodePath to persist the todo items in a file. While updating my ArrayList<String> to `task` object types that I have created, I needed to change my way of reading and writing files. Also upon upgrading this to SQLite, I have also faced some issues with ContentProviders. But all of this was a great learning experience. I will continue to work on this over March 4th and 5th weekend. I realize that would be past the final submission deadline so please check my tags to see the version submitted before March 3. I'm hoping to finish the rest of the optional implementations and the additional features that I would like to implement. 

## License

    Copyright [2017] [Leo Kim]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
