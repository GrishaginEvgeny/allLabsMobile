import 'package:flutter/material.dart';
import 'dart:math' as math;

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(),
      debugShowCheckedModeBanner: false,
    );
  }
}

class MyHomePage extends StatefulWidget {
  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  final List<CardInfo> cards = [];
  final TextEditingController nameController = TextEditingController();
  bool isError = false;

  void addCard() {
    if (nameController.text.isNotEmpty) {
      setState(() {
        cards.add(CardInfo(id: math.Random().nextInt(1000000).toString(), name: nameController.text));
        isError = false;
      });
      nameController.clear();
    } else {
      setState(() {
        isError = true;
      });
    }
  }

  void deleteCard(String id) {
    setState(() {
      cards.removeWhere((card) => card.id == id);
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Flutter Demo'),
      ),
      body: Padding(
        padding: EdgeInsets.all(10),
        child: Column(
          children: [
            Row(
              children: [
                Expanded(
                  child: TextField(
                    controller: nameController,
                    decoration: InputDecoration(
                      labelText: 'Name here',
                      errorText: isError ? 'Name is invalid' : null,
                    ),
                  ),
                ),
                IconButton(
                  icon: Icon(Icons.add),
                  onPressed: addCard,
                ),
                IconButton(
                  icon: Icon(Icons.clear),
                  onPressed: () => nameController.clear(),
                ),
              ],
            ),
            Expanded(
              child: cards.isEmpty
                  ? Center(
                child: Text(
                  'Person list is empty.',
                  style: TextStyle(fontWeight: FontWeight.bold, fontSize: 25),
                ),
              )
                  : ListView.builder(
                itemCount: cards.length,
                itemBuilder: (context, index) {
                  return MyCard(
                    name: cards[index].name,
                    onDelete: () => deleteCard(cards[index].id),
                  );
                },
              ),
            ),
          ],
        ),
      ),
    );
  }
}

class CardInfo {
  String id;
  String name;

  CardInfo({required this.id, required this.name});
}

class MyCard extends StatelessWidget {
  final String name;
  final VoidCallback onDelete;

  MyCard({required this.name, required this.onDelete});

  @override
  Widget build(BuildContext context) {
    return Card(
      child: ListTile(
        leading: Icon(Icons.account_circle), // Replace with your icon
        title: Text(name),
        trailing: IconButton(
          icon: Icon(Icons.clear),
          onPressed: onDelete,
        ),
      ),
    );
  }
}
