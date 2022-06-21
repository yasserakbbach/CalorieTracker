# CalorieTracker
Simple Calorie Tracker App with Clean Architecture, Multi-Module, Dagger Hilt and Compose. it's based on [Philipp Lackner](https://www.youtube.com/c/PhilippLackner)'s course [Learn to Build Industry-Level Android Apps](https://pl-coding.com/multi-module-course).

The **modulirization strategy** that was used in this project is by **feature** and **layer**.

## Demo

https://user-images.githubusercontent.com/21147033/174782210-ace6c6b2-66db-4834-a32d-bc3ba05b553b.mp4


## Technologies
* Jetpack Compose and Compose Navigation.
* Coroutines.
* Dagger Hilt.
* Material3 and Coil.
* Retrofit and Moshi.
* Room.
* Testing: JUnit4, Truth, Mockk, MockWebServer and End 2 End testing.

## Composables
| Name | Image |
| -------- | -------- |
| [UnitTextField](https://github.com/yasserakbbach/CalorieTracker/blob/main/onboarding/onboarding_presentation/src/main/java/com/yasserakbbach/onboarding_presentation/components/UnitTextField.kt) | <p align="center"><img src="https://user-images.githubusercontent.com/21147033/174773071-1c3dc1ca-5c7f-405a-bbbe-812dca3e0ccd.png" width="30%" height="30%" /></p> |
| [ActionButton](https://github.com/yasserakbbach/CalorieTracker/blob/main/onboarding/onboarding_presentation/src/main/java/com/yasserakbbach/onboarding_presentation/components/ActionButton.kt) | <p align="center"><img src="https://user-images.githubusercontent.com/21147033/174774191-f3cbb185-52b5-48d7-b582-204e9475c611.png" width="30%" height="30%" /></p> |
| [SelectableButton](https://github.com/yasserakbbach/CalorieTracker/blob/main/onboarding/onboarding_presentation/src/main/java/com/yasserakbbach/onboarding_presentation/components/SelectableButton.kt) | <p align="center"><img src="https://user-images.githubusercontent.com/21147033/174774630-fc8c6be2-a3d9-4169-adcf-37e26f874f1c.png" width="30%" height="30%" /></p> |
| [NutrientsBar](https://github.com/yasserakbbach/CalorieTracker/blob/main/tracker/tracker_presentation/src/main/java/com/yasserakbbach/tracker_presentation/trackeroveriew/components/NutrientsBar.kt) | <p align="center"><img src="https://user-images.githubusercontent.com/21147033/174775935-bc28284c-ad30-4e8f-9893-1c8b262fa360.png" width="30%" height="30%" /></p> |
| [NutrientBarInfo](https://github.com/yasserakbbach/CalorieTracker/blob/main/tracker/tracker_presentation/src/main/java/com/yasserakbbach/tracker_presentation/trackeroveriew/components/NutrientBarInfo.kt) | <p align="center"><img src="https://user-images.githubusercontent.com/21147033/174776617-92d24490-01dc-46da-8a2b-39a2e2d088cc.png" width="30%" height="30%" /></p> |
| [DaySelector](https://github.com/yasserakbbach/CalorieTracker/blob/main/tracker/tracker_presentation/src/main/java/com/yasserakbbach/tracker_presentation/trackeroveriew/components/DaySelector.kt) | <p align="center"><img src="https://user-images.githubusercontent.com/21147033/174776983-0e9958eb-847f-4f24-8afc-d2fa98e7b988.png" width="30%" height="30%" /></p> |
| [ExpandableMeal](https://github.com/yasserakbbach/CalorieTracker/blob/main/tracker/tracker_presentation/src/main/java/com/yasserakbbach/tracker_presentation/trackeroveriew/components/ExpandableMeal.kt) | <p align="center"><img src="https://user-images.githubusercontent.com/21147033/174777424-c416d55c-57d3-41f6-9668-444bccb95693.png" width="30%" height="30%" /></p> |
| [TrackedFoodItem](https://github.com/yasserakbbach/CalorieTracker/blob/main/tracker/tracker_presentation/src/main/java/com/yasserakbbach/tracker_presentation/trackeroveriew/components/TrackedFoodItem.kt) | <p align="center"><img src="https://user-images.githubusercontent.com/21147033/174777909-23b8b9b9-50b6-42d6-8a93-16198b965232.png" width="30%" height="30%" /></p> |
| [AddButton](https://github.com/yasserakbbach/CalorieTracker/blob/main/tracker/tracker_presentation/src/main/java/com/yasserakbbach/tracker_presentation/trackeroveriew/components/AddButton.kt) | <p align="center"><img src="https://user-images.githubusercontent.com/21147033/174778209-237fdef4-137d-4345-a300-340dd7d223cd.png" width="30%" height="30%" /></p> |
| [SearchTextField](https://github.com/yasserakbbach/CalorieTracker/blob/main/tracker/tracker_presentation/src/main/java/com/yasserakbbach/tracker_presentation/search/components/SearchTextField.kt) | <p align="center"><img src="https://user-images.githubusercontent.com/21147033/174778705-9456c630-4662-428d-9979-d6fc0f2e8db0.png" width="30%" height="30%" /></p> |
| [TrackableFoodItem](https://github.com/yasserakbbach/CalorieTracker/blob/main/tracker/tracker_presentation/src/main/java/com/yasserakbbach/tracker_presentation/search/components/TrackableFoodItem.kt) | <p align="center"><img src="https://user-images.githubusercontent.com/21147033/174779009-18336a69-1c2f-4004-80bb-385cec56ce2d.png" width="30%" height="30%" /></p> |


## Quick Links
* [On Boarding feature](https://github.com/yasserakbbach/CalorieTracker/tree/main/onboarding).
* [Tracker Overview](https://github.com/yasserakbbach/CalorieTracker/tree/main/tracker/tracker_presentation/src/main/java/com/yasserakbbach/tracker_presentation/trackeroveriew).
* [Search food](https://github.com/yasserakbbach/CalorieTracker/tree/main/tracker/tracker_presentation/src/main/java/com/yasserakbbach/tracker_presentation/search).
* [TrackerOverview End 2 End test](https://github.com/yasserakbbach/CalorieTracker/blob/main/app/src/androidTest/java/com/yasserakbbach/calorietracker/TrackerOverviewE2E.kt).
