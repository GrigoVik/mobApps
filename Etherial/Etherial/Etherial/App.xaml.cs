using System;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace Etherial
{
    public partial class App : Application
    {
        public App()
        {
            InitializeComponent();

        }

        protected override void OnStart()
        {

            MainPage = new MainPage();
        }

        protected override void OnSleep()
        {
        }

        protected override void OnResume()
        {
        }
    }
}
