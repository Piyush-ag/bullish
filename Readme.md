This is a spring application and requires java 11 to run. 

Below are the major Model used in the Application:
1. Customer
2. Product
3. Discount
4. Basket, BasketItem
5. Receipt, ReceiptItem

Its difficult to add all the Model here for this application, so just ignoring that for now. 
Also skipping the following views that we generally create during designing a microservice: Logical View, 
Implementatin View, Process View, Deployment View

The DAO layer/Repository layer that is supposed to interact with our Data store in the submission is mocked, which means
all the data is persisted in memory under Repository module itself. So every restart of application losses all
the data created.

The system can made flexible in a lot many different dimension, however right now i have provided all the 
functionalities asked in the assessment. Depending how do we expect the system to be extended we can improve our code further. 
So we can consider this submission as a initial draft as per my understanding. The module can be further enhanced,
in terms of how we handle discount and applying discount to basket or each product, having stricter input validations 
and actually lot many other dimensions. 
So keeping it simple for now. As I have to contemplate the trade-off between time vs delivery. Further we can discuss more
about extensibility and maintainability over a call. 

HOW TO RUN ===============================

Easiest way to run this application is to open in Intellij and run the main class directly. No additional setup is required.
Assuming you have java 11 available in the machine.

Test class are present under test folder. you can use "mvn clean" and "mvn test" command from intellij to run all the test cases.
Test ensure only if any business logic change occurs then they would fail. 

Please start the application and start following this API calls step by step.

Please open swagger link for easier interaction with API's : http://localhost:8080/swagger-ui/index.html#/ 
Sample payload for each API call can be found in the images i have attached along with the submission.

Step 1. Create Customer : http://localhost:8080/swagger-ui/index.html#/customer-controller/registerCustomer
Step 2. Get Customer: http://localhost:8080/swagger-ui/index.html#/customer-controller/getCustomer
Step 3. Create Product: http://localhost:8080/swagger-ui/index.html#/admin-controller/createProduct
Step 4. Get Product: http://localhost:8080/swagger-ui/index.html#/admin-controller/getProduct
Step 5. Create Discount: http://localhost:8080/swagger-ui/index.html#/admin-controller/createDiscount
Step 6. Get Discount: http://localhost:8080/swagger-ui/index.html#/admin-controller/getDiscount
Step 7. Add product to Basket: http://localhost:8080/swagger-ui/index.html#/customer-controller/addToBasket
Step 8. Get Basket: http://localhost:8080/swagger-ui/index.html#/customer-controller/getBasket
Step 9. Get Receipt: http://localhost:8080/swagger-ui/index.html#/customer-controller/generateReceipt

also if the name of image is Step 1, then that image represents the sample for Step 1





