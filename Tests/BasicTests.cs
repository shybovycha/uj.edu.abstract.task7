using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using System.Linq.Expressions;
using System.Reflection;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using IKVM.Attributes;

namespace Tests
{
    [TestClass]
    public class BasicTests
    {
        [TestMethod]
        public void GenericTypeSignatures()
        {
            var dllType = typeof(DoubleLinkedList);
            var implementsAttribute = dllType
                .GetCustomAttributes(typeof(ImplementsAttribute), false)
                .Cast<ImplementsAttribute>()
                .FirstOrDefault();
            var iterableMessage = String.Format("Type {0} incorrect generic interface {1} implementation. Remember to give a name T to generic parameter.", dllType.Name, typeof(java.lang.Iterable).FullName);
            Assert.IsTrue(implementsAttribute.Interfaces.Contains(typeof(java.lang.Iterable).FullName));
            var dllSignatureAttribute = dllType
               .GetCustomAttributes(typeof(SignatureAttribute), false)
               .Cast<SignatureAttribute>()
               .FirstOrDefault();
            Assert.AreEqual("<T:Ljava/lang/Object;>Ljava/lang/Object;Ljava/lang/Iterable<TT;>;", dllSignatureAttribute.Signature, iterableMessage);
            var elementIncorectSignature = String.Format("Type {0} incorrect generic signature.", typeof(Element).FullName);
            var elementSignatureAttribute = typeof(Element)
               .GetCustomAttributes(typeof(SignatureAttribute), false)
               .Cast<SignatureAttribute>()
               .FirstOrDefault();
            Assert.AreEqual("<T:Ljava/lang/Object;>Ljava/lang/Object;", elementSignatureAttribute.Signature, elementIncorectSignature);
        }

        [TestMethod]
        public void GenericMethodSignatures()
        {
            Func<Expression<Func<object>>, string> getConstructorSignature = e =>
                {
                    var ne = e.Body as NewExpression;
                    var signature = ne.Constructor.GetCustomAttributes<SignatureAttribute>().FirstOrDefault();
                    return signature != null ? signature.Signature : null;
                };
            Func<Expression<Action>, string> getMethodSignature = e =>
                {
                    var mce = e.Body as MethodCallExpression;
                    var signature = mce.Method.GetCustomAttributes<SignatureAttribute>().FirstOrDefault();
                    return signature != null ? signature.Signature : null;
                };
            DoubleLinkedList dllFake = null;
            Element eFake = null;
            var dllConstructor = String.Format("Incorrect signature or type {0}<T> constructor accepting {1}<T>.", typeof(DoubleLinkedList).FullName, typeof(java.lang.Iterable).FullName);
            Assert.AreEqual("(Ljava/lang/Iterable<TT;>;)V", getConstructorSignature(() => new DoubleLinkedList((java.lang.Iterable)null)), dllConstructor);
            var dllIteratorMethor = String.Format("Incorrect signature of method returning {1}<T> in type {0}<T>.", typeof(DoubleLinkedList).FullName, typeof(java.util.Iterator).FullName);
            Assert.AreEqual("()Ljava/util/Iterator<TT;>;", getMethodSignature(() => dllFake.iterator()));
            var getterSignaureInList = String.Format("Incorrect getter method signature in type {0}<T>.", typeof(DoubleLinkedList).FullName);
            Assert.AreEqual("()LElement<TT;>;", getMethodSignature(() => dllFake.getHead()), getterSignaureInList);
            Assert.AreEqual("()LElement<TT;>;", getMethodSignature(() => dllFake.getTail()), getterSignaureInList);
            var useGenericParameterMessage = String.Format("Method of type {0}<T> should be using generic parameter T.", typeof(Element).FullName);
            Assert.AreEqual("()LElement<TT;>;", getMethodSignature(() =>eFake.getNext()), useGenericParameterMessage);
            Assert.AreEqual("(LElement<TT;>;)V", getMethodSignature(() => eFake.setNext((Element)null)), useGenericParameterMessage);
            Assert.AreEqual("()TT;", getMethodSignature(() => eFake.getValue()), useGenericParameterMessage);
            Assert.AreEqual("(TT;)V", getMethodSignature(() =>eFake.setValue(null)), useGenericParameterMessage);
            Assert.AreEqual("()LElement<TT;>;", getMethodSignature(() => eFake.getPrevious()), useGenericParameterMessage);
            Assert.AreEqual("(LElement<TT;>;)V", getMethodSignature(() =>eFake.setPrevious((Element)null)), useGenericParameterMessage);
        }

        [TestMethod]
        public void ListConstructorsAndGetters()
        {
            var dll1 = new DoubleLinkedList();
            Assert.IsNull(dll1.getHead());
            Assert.IsNull(dll1.getTail());
            var names = new java.util.ArrayList();
            names.add("Ala");
            names.add("Ola");
            names.add("Ula");
            var dll2 = new DoubleLinkedList(names);
            Assert.AreEqual("Ala", dll2.getHead().getValue());
            Assert.AreEqual("Ula", dll2.getTail().getValue());
            Assert.IsTrue(dll2.Cast<string>().SequenceEqual(new string[] { "Ala", "Ola", "Ula" }));
        }

        [TestMethod]
        public void ReverseSequence()
        {
            var dll = new DoubleLinkedList();
            dll.add(5);
            dll.add(10);
            dll.add(15);
            Assert.IsTrue(dll.Cast<int>().SequenceEqual(new int[] { 5, 10, 15 }));
            dll.reverse();
            Assert.IsTrue(dll.Cast<int>().SequenceEqual(new int[] { 15, 10, 5 }));
        }

        [TestMethod]
        public void InsertElements()
        {
            var dll = new DoubleLinkedList();
            dll.add(5);
            dll.add(10);
            dll.add(15);
            Assert.IsTrue(dll.Cast<int>().SequenceEqual(new int[] { 5, 10, 15 }));
            dll.add(1, 20);
            Assert.IsTrue(dll.Cast<int>().SequenceEqual(new int[] { 5, 20, 10, 15 }));
            dll.add(4, 25);
            Assert.IsTrue(dll.Cast<int>().SequenceEqual(new int[] { 5, 20, 10, 15, 25 }));
            dll.add(0, 30);
            Assert.IsTrue(dll.Cast<int>().SequenceEqual(new int[] { 30, 5, 20, 10, 15, 25 }));
        }

        [TestMethod]
        public void IterateManually()
        {
            var dll = new DoubleLinkedList();
            var numbers = new List<int> { 5, 10, 15 };
            foreach (var number in numbers)
            {
                dll.add(number);
            }
            int x = 0;
            for (var i = dll.iterator(); i.hasNext(); )
            {
                Assert.AreEqual(numbers[x++], i.next());
            }
        }

        [TestMethod]
        public void ValidateIndexes()
        {
            var dll = new DoubleLinkedList();
            dll.add(5);
            dll.add(10);
            dll.add(15);
            var forbidden = new Action[]
            {
                () => dll.get(3),
                () => dll.removeAt(-1),
                () => (new DoubleLinkedList()).get(0)
            }.ToList();
            foreach (var action in forbidden)
            {
                try
                {
                    action();
                }
                catch (java.lang.IndexOutOfBoundsException)
                {
                }
                catch (System.IndexOutOfRangeException)
                {
                }
            }
        }

        [TestMethod]
        public void AcquireElements()
        {
            var dll = new DoubleLinkedList();
            dll.add(5);
            dll.add(10);
            dll.add(15);
            Assert.AreEqual(3, dll.size());
            Assert.AreEqual(10, dll.get(1));
            try
            {
                Assert.AreEqual(20, dll.get(3));
            }
            catch (java.lang.IndexOutOfBoundsException)
            {
            }
            dll.add(20);
            Assert.AreEqual(4, dll.size());
        }

        [TestMethod]
        public void DropElements()
        {
            var numbers = new java.util.ArrayList();
            numbers.add(4.13);
            numbers.add(4.7);
            numbers.add(5.8);
            var dll = new DoubleLinkedList(numbers);
            Assert.AreEqual(3, dll.size());
            dll.add(5.1);
            Assert.AreEqual(4, dll.size());
            dll.removeAt(1);
            Assert.AreEqual(3, dll.size());
            Assert.IsTrue(dll.Cast<double>().SequenceEqual(new double[] { 4.13, 5.8, 5.1 }));
            while (dll.size() > 0)
            {
                dll.removeAt(0);
            }
            Assert.IsNull(dll.getHead());
            Assert.IsNull(dll.getTail());
        }
    }
}
