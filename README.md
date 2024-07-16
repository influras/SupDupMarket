<h1>Supermarket Shelf Management</h1>

<p>This Maven project provides a console application for managing supermarket inventory, including tracking product quality, pricing, and automatically removing expired or unsuitable products from the shelf.</p>

<h1>Features</h1>
<ul>
    <li>Track product quality and price over time.</li>
    <li>Remove expired or unsuitable products automatically.</li>
    <li>Update product information daily.</li>
    <li>Import and Export products via csv.</li>
</ul>

<h1>Prerequisites</h1>
Make sure you have the following installed on your machine:
<ul>
<li>Java Development Kit (JDK) version 21 or higher</li>
<li>Maven build tool</li>
<li>Optional: IDE with integrated maven functionalities</li>    
</ul>

<h2>Getting Started</h2>
<h4>Clone the repository:</h4> 
<pre><code>git clone https://github.com/influras/SupDupMarket.git </code></pre>
<h4>Navigate to the project repository</h4> 
<pre><code> cd projectdirectory </code></pre>
<h4>Build the Project</h4>
<pre><code> mvn clean install </code></pre>       
 This command compiles the code, runs tests and displays the products

<h1>Design Patterns</h1>
<p>This project leverages various design patterns to enhance flexibility and maintainability:</p>
<ol>
<li>Observer Pattern: Decouples product monitoring from shelf management for increased flexibility.</li> 

<li>Factory Method Pattern: Centralizes the creation of product instances based on specific rules.</li>
</ol>
