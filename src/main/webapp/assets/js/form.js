function toggle(option)
{
    if(option==='hide') {
        document.getElementById("payment").style.display="none";
        document.getElementById("card_number").value="";
        document.getElementById("valid_thru").value="";
        document.getElementById("cvv_number").value="";
    }
    else
        document.getElementById("payment").style.display="block";
}