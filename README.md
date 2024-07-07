<h1>Supermarket Inventory Management</h1>

<p>This Maven project provides a console application for managing supermarket inventory, including tracking product quality, pricing, and automatically removing expired or unsuitable products from the shelf.</p>

<h1>Features</h1>
<ul>
    <li>Track product quality and price over time.</li>
    <li>Remove expired or unsuitable products automatically.</li>
    <li>Update product information daily.</li>
</ul>

<h1>Installation</h1>
<ol>
    <li>Clone the repository:
        <pre><code>git clone https://github.com/yourusername/supdupmarket.git</code></pre>
    </li>
    <li>Navigate to the project directory:
        <pre><code>cd supdupmarket</code></pre>
    </li>
    <li>Compile and package the project using Maven:
        <pre><code>mvn clean package</code></pre>
    </li>
</ol>

<h1>Usage</h1>
<p>To run the application, use the following Maven command:</p>
<pre><code>mvn exec:java -Dexec.mainClass="de.gribovskij.supdupmarket.Main"</code></pre>

<p>This will start the application and display the product inventory, updating it for the specified number of days and removing expired or unsuitable products as needed.</p>

<h1>Project Structure</h1>
<ul>
    <li><code>src/main/java</code>: Java source files</li>
    <li><code>pom.xml</code>: Maven project configuration</li>
</ul>

<h1>Design Patterns</h1>
<p>This project leverages various design patterns to enhance flexibility and maintainability:</p>
<ol>
<li>Strategy Pattern: Enables dynamic definition of different behaviors for products.</li>

<li>Observer Pattern: Decouples product monitoring from shelf management for increased flexibility.</li> 

<li>Factory Method Pattern: Centralizes the creation of product instances based on specific rules.</li>
</ol>

These patterns contribute to structuring the code cleanly and facilitate the integration of new product rules and features.
