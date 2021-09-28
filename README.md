# weeklycalendar

Note : Debug apk has been given in root directory of the project named "app-debug.apk".
       Please check it out!

Introduction : 
    
    This is a calendar app. here you can add your important event at a specific date.
    you can update and delete the event details. create an account to keep your data synced with server.

Technology Used :

    1. Firebase ( For authonication and data sync)
    2. Kotlin   ( Main app language)
    3. MVVM     (Easy for maintaining code structure)
    4. LiveData (To keep data synced with UI)
    5. Recyclerview (To show data)

Approach : 
    
    It's a very straight forward approach to solve this problem. I created and event data class
    and datewise data class which includes list of that dates event class. create an account
    under an user in server. keep his all data structued date wise. when user update his/her event.
    I update that in that specific date. similarly create and delete data happen. I used
    recylerview to show 7 days in a week. then in each day used nested recylcerview to show
    every event. previous and next button works on startdate of the week. it changes the
    start day of the week by 7 days. then based on that date reload data of that week.

Future Plan :
    
    This project has many open ends and I've plan to do it in future. Some of that plans
    are UI Update(Of course), Show daily and monthly view. set reminder etc.

Not Used Recommended Tools :
    
    1. Room ( Firebase has local caching. app will work offile. I've plan to updateData model
              in future. So Will integrate room later).
    2. JetPack ( I'm not confident at this technology. Currently studying about this.
                 will use this in future)
    