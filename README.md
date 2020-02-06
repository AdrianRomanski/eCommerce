# eCommerce Application

## Password validation 
- Password and confirm password are equal
- Longer than 6 signs
- Made from letters and digits 
- No special signs or whitespaces allowed
- Proper logging included

## Jenkins set up using Microsoft Azure

## oAuth secured 
- Proper JWT headers are present and utilized correctly.

#### The following are only accessible after authenticated:
- Cart: addToCart, removeFromCart
- Item: getItems, getItemById, getItemsByName
- Order: submit, getOrdersForUser
- User: findById, findByUserName

## Tests written and meet an acceptable code coverage level.
![100Tests](https://user-images.githubusercontent.com/44946000/73930835-83863280-48d7-11ea-9b4f-16e426648484.png)

## Correct metrics are identified for logging, in order to monitor a system. Metrix are indexed to Splunk.

#### Most popular error while creating password 
![passworderrors](https://user-images.githubusercontent.com/44946000/73931626-e62bfe00-48d8-11ea-9723-ba7b4786fc67.jpg)

#### Alert set up weekly for next errors 
![alert](https://user-images.githubusercontent.com/44946000/73931886-6bafae00-48d9-11ea-8c33-3dfd9061e88d.jpg)
