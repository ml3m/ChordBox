# ChordBox

## Work in progress..

   <div align="center">
     <img src="assets/chordbox_logo.png" alt="Project logo" width="250"/>
   </div>

```bash
                          +-------------------------+
                          |       com.chordbox.models.Item              |
                          +-------------------------+
                          | - name: String          |
                          | - price: double         |
                          +-------------------------+
                          | + getName(): String     |
                          | + getPrice(): double    |
                          +-------------------------+
                                    ^
                                    |
                +--------+----------+-------------------+
                |        |          |                   |
    +------------------+ | +-----------------+  +-----------------+
    |       com.chordbox.models.CD         | | |       com.chordbox.models.Disk      |  |    com.chordbox.models.Instrument   |
    +------------------+ | +-----------------+  +-----------------+
    | + sell(): void   | | | + sell(): void  |  | + sell(): void  |
    +------------------+ | +-----------------+  +-----------------+
                         |
                +-------------------------+
                |       com.chordbox.models.Poster            |
                +-------------------------+
                | + sell(): void          |
                +-------------------------+


+----------------------------------+      +------------------------------------+
|           com.chordbox.utils.Sellable               |      |          com.chordbox.utils.Discountable              |
+----------------------------------+      +------------------------------------+
| <interface>                      |      | <interface>                        |
| + sell(): void                   |      | + applyDiscount(): double          |
+----------------------------------+      +------------------------------------+

+----------------------------------+      +------------------------------------+
|           com.chordbox.models.Discount               |      |           com.chordbox.models.Order                    |
+----------------------------------+      +------------------------------------+
| - amount: double                 |      | - item: com.chordbox.models.Item                       |
| - type: String                   |      | - discounts: com.chordbox.models.Discount[]            |
+----------------------------------+      +------------------------------------+
| + com.chordbox.models.Discount(amount, type)         |      | + com.chordbox.models.Order(item, discounts)           |
| + calculateDiscount(price):double|      | + applyDiscount(): double          |
+----------------------------------+      +------------------------------------+

+----------------------------------+      +------------------------------------+
|           com.chordbox.models.Customer               |      |           com.chordbox.models.Payment                  |
+----------------------------------+      +------------------------------------+
| - name: String                   |      | - method: String                   |
| - email: String                  |      | - amountPaid: double               |
+----------------------------------+      +------------------------------------+
| + getName(): String              |      | + processPayment(): void           |
| + getEmail(): String             |      +------------------------------------+
+----------------------------------+

+----------------------------------+      +------------------------------------+
|         com.chordbox.utils.InputDevice              |      |         com.chordbox.utils.OutputDevice               |
+----------------------------------+      +------------------------------------+
| - random: Random                 |      |                                    |
+----------------------------------+      |                                    |
| + getType(): String              |      | + writeMessage(message: String)    |
| + nextInt(): Integer             |      | + printArray<T>(array: T[]): void  |
| + getLine(): String              |      +------------------------------------+
| + getNumbers(N: int): Integer[]  |
+----------------------------------+

                          +-------------------------+
                          |     com.chordbox.MusicStoreSystem    |
                          +-------------------------+
                          |   <Main Class>          |
                          +-------------------------+
                          | + main(args: String[])  |
                          +-------------------------+
```
