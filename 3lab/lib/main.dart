import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

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
  String message = '';
  bool isNameInvalid = false;

  bool isValidName(String name) {
    return RegExp(r'^[a-zA-Z-]+$').hasMatch(name);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Flutter Demo'),
      ),
      body: Padding(
        padding: EdgeInsets.all(16.0),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.start,
          children: <Widget>[
            Text(
              "Enter your name:",
              style: TextStyle(fontSize: 15, fontFamily: 'Monospace'),
            ),
            TextField(
              onChanged: (value) {
                setState(() {
                  message = value;
                  isNameInvalid = !isValidName(message);
                });
              },
              decoration: InputDecoration(
                labelText: 'Your name here',
                errorText: isNameInvalid ? 'Name is invalid' : null,
              ),
              style: TextStyle(fontSize: 25),
            ),
            if (isNameInvalid)
              Text(
                "Name is invalid",
                style: TextStyle(fontSize: 15, color: Colors.red, fontFamily: 'Monospace'),
              ),
            Text(
              message.isNotEmpty && !isNameInvalid ? "Hello, $message!" : "",
              style: TextStyle(fontSize: 28, fontFamily: 'Cursive'),
            ),
            ElevatedButton(
              onPressed: () {
                if (message.isNotEmpty && !isNameInvalid) {
                  _showMyDialog("Your name is $message!");
                } else {
                  _showMyDialog("You didn't enter your name or your name is invalid");
                }
              },
              child: Text("Show name in alert"),
            ),
          ],
        ),
      ),
    );
  }

  Future<void> _showMyDialog(String content) async {
    return showDialog<void>(
      context: context,
      barrierDismissible: false,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text(content),
          actions: <Widget>[
            TextButton(
              child: Text('Dismiss'),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      },
    );
  }
}
