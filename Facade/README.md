# DESIGN PATTERNS

### FACADE EXAMPLE IMPLEMENTATION

This project is an example implementation of the facade design pattern. It essentially has a facade to book a room
or to cancel a room booking. It is also composed by managers to deal with invoicing, refunding, logging and room reservation.

Every action in the facade workflow involves a booking contract, which is a document that has information about
a booking, from the moment when the customer requests a booking - a room isn't reserved yet - until the moment
the booking is finished and the customer leaves the hotel.

In practice, this would be a much bigger and complex system, and the facade would probably be called by another
activity, but this isn't part of the scope for this project. The intention is to just implement a facade.
