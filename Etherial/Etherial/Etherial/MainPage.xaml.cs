using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Forms;

namespace Etherial
{

    public partial class MainPage : ContentPage
    {
        public MainPage()
        {
            InitializeComponent();
            GameView gameView = new GameView();
            Content = new StackLayout { Children = { gameView } };
        }
    }
}
