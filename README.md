
<img src="https://github.com/mshearer123/JetMovieDB/blob/master/images/list.png" width="240" height="400">       <img src="https://github.com/mshearer123/JetMovieDB/blob/master/images/details.png" width="240" height="400">


### UI

The app has an extremely small feature set so I wanted to keep the UI pretty simple.  Google seems to be moving things more and more towards the bottom of the screen, so I put the toolbar there so it's easy to click.  

The white navigation, status and background are modelled off the google tasks app.

### Architecture

I've been building android apps using MVP ( + Interactor) architecture for the last couple of years.  Although, now I prefer google's MVVM framework.  While in MVP lifecycle and order of execution methods is a concern.   MVVM removes this concern and allows logical code to run without worrying about what order commands are pushed to the view.   I feel the interactor + repository pattern still fits fine with MVVM.


I considered using data binding because it fits well with MVVM but some people hate it with a passion, so I left it out.


### Setup
Android studio 3.2 RC 2+

Gradle wrapper 4.9

or  

I have added a debug apk to the root directory

### CI tools 
#### [Bitrise](https://app.bitrise.io/apps/add) 

I integrated the github repo with bitrise so that I would get some feedback on PRs before they were merged.

### libraries 

#### [androidx](https://developer.android.com/topic/libraries/support-library/androidx-overview)

I have used small bits of the androidx library before but not to the degree I have used it here.   

I decided early on that I'd try to use the most up to date google libraries throughout.   (This turned out to be tricky as most 3rd party libs do not support it)


#### [room](https://developer.android.com/training/data-storage/room/)

I'm not sure if Room is considered 3rd party.  However I choose to use Room over other persistence libraries because it plays nicely with the androidx paging library and avoids adding a non-google 3rd party.


#### [Paginate](https://github.com/MarkoMilos/Paginate)

Although the app worked with pure andoidx.paging I wasn't very happy with how some it worked with the [PagedList.BoundaryCallback](https://developer.android.com/reference/android/arch/paging/PagedList.BoundaryCallback).   Adding the Paginate library cleaned up a lot of the code.   It became a simple case of attaching the adapter to a room datasource and letting the view model update the data when requested.

Note - Paginate doesn't support androidx, so I manually added the library and updated it.


#### [rxjava2](https://github.com/ReactiveX/RxJava)

I'm a fan of how rxjava and retrofit combine to make handling apis and data straightforward. 
Kotlin has a lot of the functions that rx has provided for java apps so I would definitely consider coroutines as a viable replacement for apps using rx.


#### [retrofit](https://square.github.io/retrofit/) [okhttp](http://square.github.io/okhttp/)

Retorfit, okhttp and gson are a pretty much the gold standard in android networking libraries at the minute so I'd choose them for any new work.


#### [koin](https://github.com/InsertKoinIO/koin)

We use Dagger 2 extensively in my current job but I'm always curious about alternatives. 
Arguing between the difference of Dagger and a service locator like koin seems to be the topic of the moment in the android world.  I like koin because for simple projects it is extremely straightforward.   It also prints out readable error messages, which Dagger does not.


#### [picasso](http://square.github.io/picasso/)

There are quite a few good options for image processing libraries at the minute, picasso, glide and fresco.
I'm probably more used to using glide, but as picasso 3 is about to be released I chose to use picasso as it is being more actively developed on.


### debug libraries 

#### [stetho](http://facebook.github.io/stetho/)

Steho allows you to easily debug network traffic and your local db through chrome.  This is great for both debugging and attaching useful screenshots to PRs that need to explain db changes or networking changes.


### test libraries 

#### [mockito-kotlin](https://github.com/nhaarman/mockito-kotlin)

Helps writing cleared tests with kotlin.


#### [truth](https://github.com/google/truth)

I really like the truth syntax and find tests much more readable when it's used.

