# Shopping App
Shopping App is an application where users can view a list of products, see their details, and add or remove them from the cart.

---
## Requirements
- Android Studio
- An Android mobile device or Android Emulator

(Developed in `Android Studio Hedgehog (version 2023.1.1 Patch 2`)

---
## Installation and Run
1.Download or clone the project from GitHub.

2.Open the project in Android Studio.

3.Install dependencies.

4.If the local.properties file is not created automatically, create it and set your own directory like this: 
`sdk.dir=C\:\\Users\\user1\\AppData\\Local\\Android\\Sdk`

5.Run the project on an emulator or a real device.

---
## Usage
#### Products Page
On this page, the products retrieved from the API are listed. Each product is displayed with information such as its image, name and price. Users can view the details of the desired product, add it to the cart, or remove it from the cart.

![ss1]() ![ss1]()

#### Product Detail Page
When the user clicks on any product from the Products Page, they are directed to this page.
Detailed information about the product is displayed on this page. Users can also add or remove the product from the cart.

![ss1]() ![ss1]()

#### Shopping Cart Page
Users are redirected to this page by clicking on the Cart from the Products and Product Detail pages.
On this page, the products in the user's cart are listed. Users can view, increase or decrease the quantity, or remove items from their carts. Additionally, the total amount is displayed, and users can complete the order on this page.

![ss1]() ![ss1]() ![ss1]()

---
## Libraries
- [AndroidX Navigation](https://developer.android.com/jetpack/androidx/releases/navigation) - It is used to ensure an orderly transition between pages.
- [Retrofit](https://square.github.io/retrofit/) - It is used to make API calls and exchange data.
- [Dagger Hilt](https://dagger.dev/hilt/) - It is used to better manage and inject dependencies.
- [Gson Converter](https://github.com/square/retrofit/tree/master/retrofit-converters/gson) - Used with Retrofit to process JSON data from the API.
- [RecyclerView](https://developer.android.com/jetpack/androidx/releases/recyclerview) - It is used to create and manage list views.
- [Glide](https://github.com/bumptech/glide) - It is used to upload and display images.

---
## Technical Specifications
The application is designed using Model-View-ViewModel (MVVM) architecture.
#### Data
Models, services and repositories in the project are located in this layer.

-**models:** A common model named Product is used to hold the data coming from the API as an object model. Two helper models are used to access products in the received data.
-**repository:** Used to retrieve product data. It retrieves data using the ApiService interface. An asynchronous operation is provided with Flow.
-**service:** It reaches the endpoints of the API and enables the relevant operation (GET) to be performed.
#### DI
It ensures that structures such as Retrofit and Repository used in the project are created once and managed from a single place.
#### UI
The screens in the project and the structures they contain are located in this layer. A common toolbar is used for each page.

-**details:** Contains a Fragment that performs functions on the Detail page. It communicates with 2 ViewModels to update the UI or ViewModel data.
-**productlisting:** Displays data retrieved from the API. Two different RecyclerViews are used. The relevant Adapter classes are also included here. It communicates with 2 ViewModels to update the UI or ViewModel data.
-**shoppingcart:** Deals with operations related to the cart page. This page contains 2 RecyclerViews. The adapters for these are also included here. It updates the UI or ViewModel data by communicating with 2 ViewModels. While sorting the product items, the Divider class is used to separate the items from each other.
#### Utils
Contains constants and extension methods used in the project.
#### ViewModels
Two ViewModels are used: ProductViewModel for products and CartViewModel for cart operations.

-**ProductViewModel:** Manages the business logic of products. The Repository class is injected and used. Retrieves and lists products and suggested products. Also keeps the selected product for the detail page. Results are communicated to the UI via LiveData.
-**CartViewModel:** Manages the business logic of the cart. Adds, removes, and clears products from the user's cart. Also keeps the products in the cart and the total price. This ViewModel allows users to interact with the cart and update its status. Results are communicated to the UI via LiveData.

---
## Tests
Application tested on the following devices:
- Pixel 2 (API 30)
- Pixel 5 (API 34)
- Pixel 7 pro (API 30)
- Xiaomi Redmi Note 9 Pro (API 29)

